package com.lolport.capture.overlay;

import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.util.Objects;

import static java.awt.Toolkit.getDefaultToolkit;

public class ChatOverlayImpl implements OverLay {
    private Canvas canvas;
    private GraphicsContext gc;

    public static Rectangle box;

    private Pane fullScreenPane;

    private final Stage stage;
    private final Scene scene;
    private final double stageX;
    private final double stageY;

    private Scene newScene;

    private double initMouseX;
    private double initMouseY;
    private double initWidth;
    private double initHeight;
    private double initLayoutX;
    private double initLayoutY;
    private String resizeDirection = "default"; // 현재 크기 조정 방향


    public ChatOverlayImpl(Stage stage, Scene settingScene, Node contentSide) {
        this.stage = stage;
        this.scene = settingScene;
        this.stageX = stage.getX();
        this.stageY = stage.getY();

        // 기존 프로그램들은 투명화하여 보이지 않게 한다. 단, 이 클래스가 종료 될 때 원상 복구 해줘야함 -> key리스너 이용
//        settingScene.setFill(Color.TRANSPARENT);
//        contentSide.setOpacity(0);

        Pane p = new Pane();
//        newScene = new Scene(p, );
//        stage.setScene(newScene);
        // 회색 반투명 화면 생성
        drawTransGray();
        drawOverlayBox();
    }

    @Override
    public void drawTransGray() {
        Dimension screen = getDefaultToolkit().getScreenSize();
        this.canvas = new Canvas(screen.getWidth(), screen.getHeight());
        this.gc = canvas.getGraphicsContext2D();
        gc.setFill(javafx.scene.paint.Color.web("#808080", 0.2));
        gc.fillRect(0, 0, screen.getWidth(), screen.getHeight());



        fullScreenPane = new Pane();
        fullScreenPane.getChildren().add(this.canvas);
        fullScreenPane.setBackground(Background.fill(Color.web("#000000", 0)));


        // 마우스 위치에 따라서 이벤트 적용
        fullScreenPane.setOnMouseMoved(mouseEvent -> {
            if (box == null) return; // box가 아직 활성화 되지 않았다면 함수 종료해줌 근데 이 메서드가 꼭 필요한건지는 모르겠음 ???

            resizeDirection = checkMousePos(mouseEvent.getX(), mouseEvent.getY());
            changeMouseCursor(fullScreenPane, resizeDirection); // 마우스커서를 화살표로 바꿔줌
        });

        // 마우스 클릭시 초기값 저장
        fullScreenPane.setOnMousePressed(mouseEvent -> {
            initMouseX = mouseEvent.getX();
            initMouseY = mouseEvent.getY();
            initWidth = box.getWidth();
            initHeight = box.getHeight();
            initLayoutX = box.getLayoutX();
            initLayoutY = box.getLayoutY();
        });
        // 오버레이 사각형 크기 조절하는 마우스 드래그 이벤트
        fullScreenPane.setOnMouseDragged(mouseEvent -> {
            double deltaX = mouseEvent.getX() - initMouseX;
            double deltaY = mouseEvent.getY() - initMouseY;

            switch (resizeDirection) {
                case "leftSide":
                    if (initWidth - deltaX > 10) { // 최소 크기 제한
                        box.setLayoutX(initLayoutX + deltaX);
                        box.setWidth(initWidth - deltaX);
                    }
                    break;
                case "rightSide":
                    if (initWidth + deltaX > 10) {
                        box.setWidth(initWidth + deltaX);
                    }
                    break;
                case "topSide":
                    if (initHeight - deltaY > 10) {
                        box.setLayoutY(initLayoutY + deltaY);
                        box.setHeight(initHeight - deltaY);
                    }
                    break;
                case "bottomSide":
                    if (initHeight + deltaY > 10) {
                        box.setHeight(initHeight + deltaY);
                    }
                    break;
                default:
                    break;
            }
        });

        newScene = new Scene(fullScreenPane, screen.getWidth(), screen.getHeight(), Color.TRANSPARENT);

        // ESC 누르면 설정창 종료하고 애플리케이션 다시 띄우기 이벤트 핸들러
        newScene.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ESCAPE) {
                stage.setX(stageX);
                stage.setY(stageY);
                stage.setScene(this.scene);
                File conf = new File(Objects.requireNonNull(getClass().getResource("/\\.lolport\\.conf")).toString());
            }
        });
//        newScene.setRoot(fullScreenPane);
        stage.setScene(newScene);
        stage.setX(0);
        stage.setY(0);
    }

    // 오버레이 사각형 주의에 마우스가 오면 마우스 커서를 알맞은 화살표로 바꿔주는 메서드
    private void changeMouseCursor(Pane fullScreenPane, String pos) {
        switch (pos) {
            case "leftSide":
            case "rightSide":
                javafx.scene.image.Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/setting/mouseHorizontalArrow.png")).toString());
                fullScreenPane.setCursor(new ImageCursor(image, image.getWidth() / 2, image.getHeight() / 2));
                break;
            case "topSide":
            case "bottomSide":
                image = new Image(Objects.requireNonNull(getClass().getResource("/images/setting/mouseVerticalArrow.png")).toString());
                fullScreenPane.setCursor((new ImageCursor(image, image.getWidth() / 2, image.getHeight() / 2)));
                break;
            default:
                fullScreenPane.setCursor(Cursor.DEFAULT);
                break;
        }
    }

    // 마우스가 오버레이 박스의 어느 위치에 있는지 체크
    private String checkMousePos(double mouseX, double mouseY) {
        Bounds bounds = box.localToScene(box.getBoundsInLocal());
        double boxX = bounds.getMinX();
        double boxY = bounds.getMinY();

        double width = box.getWidth();
        double height = box.getHeight();

        double boundary = 10;

        if (boxX + width < mouseX && mouseX < boxX + width + boundary && boxY < mouseY && mouseY < boxY + height) {
            return "rightSide";
        } else if (boxX - boundary < mouseX && mouseX < boxX && boxY < mouseY && mouseY < boxY + height) {
            return "leftSide";
        } else if (boxX < mouseX && mouseX < boxX + width && boxY - boundary < mouseY && mouseY < boxY) {
            return "topSide";
        } else if (boxX < mouseX && mouseX < boxX + width && boxY + height < mouseY && mouseY < boxY + height + boundary) {
            return "bottomSide";
        } else {
            return "default";
        }


    }

    @Override
    public void removeTransGray() {

    }

    @Override
    // 여기서 생성한 fullScreenPane을 Scene에 붙여야함
    public void drawOverlayBox() {
        // 설정을 저장하고 다시 애플리케이션으로 돌아가기 위한 설명 Label
        Label returnLabel = new Label("설정을 완료 하면 Esc를 눌러주세요.");
        returnLabel.setStyle("-fx-font-size:30; -fx-font-family:'Noto Sans KR'; -fx-text-fill:white;-fx-background-color:black");
        returnLabel.setAlignment(Pos.TOP_CENTER);
//        fullScreenPane = new Pane();
        box = new Rectangle(100, 100, Color.web("#000000", 0.01));
//        box.setOpacity(0.1);
        box.setStroke(Color.BLUE);
        box.setStrokeType(StrokeType.INSIDE);
        box.getStrokeDashArray().addAll(2d, 13d);
        box.setStrokeWidth(3.0);
        box.setLayoutX(500);
        box.setLayoutY(500);
        fullScreenPane.getChildren().addAll(box, returnLabel);

        // Initial offsets for dragging
        final double[] offsetX = {0};
        final double[] offsetY = {0};

        // Mouse pressed event
        box.setOnMousePressed(event -> {
            offsetX[0] = event.getSceneX() - box.getTranslateX();
            offsetY[0] = event.getSceneY() - box.getTranslateY();

            gc.setFill(Color.web("#808080", 0.2));
            gc.fillRect(offsetX[0], offsetY[0], 100, 100);
        });
        box.setOnMouseMoved(mouseEvent -> {
            double startX, endX, startY, endY;
            double width = box.getWidth();
            double height = box.getHeight();
            int depart = 20;
            startX = box.getX() + depart;
            endX = box.getX() + width - depart;
            startY = box.getY() + depart;
            endY = box.getY() + height - depart;
            if (startX < mouseEvent.getX() && mouseEvent.getX() < endX) {
                if (startY < mouseEvent.getY() && mouseEvent.getY() < endY) {
                    box.setCursor(Cursor.OPEN_HAND);
                }
            }
            else {
                box.setCursor(Cursor.DEFAULT);
            }
        });

        // Mouse dragged event
        box.setOnMouseDragged(event -> {
            box.setTranslateX(event.getSceneX() - offsetX[0]);
            box.setTranslateY(event.getSceneY() - offsetY[0]);
            double a = event.getSceneX() - offsetX[0];
            double b = event.getSceneY() - offsetY[0];

//            gc.setFill(Color.web("#FF0000", 0));
//            gc.fillRect(a,b, 100, 100);

            gc.clearRect(box.getLayoutX(), box.getLayoutY(), box.getWidth(), box.getHeight());
        });

    }
}

