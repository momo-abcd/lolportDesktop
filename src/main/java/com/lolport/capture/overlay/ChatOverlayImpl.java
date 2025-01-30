package com.lolport.capture.overlay;

import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Objects;

import static java.awt.Toolkit.getDefaultToolkit;

public class ChatOverlayImpl implements OverLay {
    private Canvas canvas;
    private GraphicsContext gc;

    private Rectangle box;

    private Pane fullScreenPane;

    private final Stage stage;
    private final Scene scene;

    private Scene newScene;

    public ChatOverlayImpl(Stage stage, Scene settingScene, Node contentSide) {
        this.stage = stage;
        this.scene = settingScene;
        // 기존 프로그램들은 투명화하여 보이지 않게 한다. 단, 이 클래스가 종료 될 때 원상 복구 해줘야함 -> key리스너 이용
        settingScene.setFill(Color.TRANSPARENT);
        contentSide.setOpacity(0);

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

            String pos = checkMousePos(mouseEvent.getX(), mouseEvent.getY());

            changeMouseCursor(fullScreenPane, pos); // 마우스커서를 화살표로 바꿔줌
        });

        fullScreenPane.setOnMouseClicked(mouseEvent -> {
        });
        fullScreenPane.setOnMouseDragged(mouseEvent -> {

            switch (checkMousePos(mouseEvent.getX(), mouseEvent.getY())) {
                case "leftSide":
                    box.setWidth(box.getLayoutX() - mouseEvent.getX() + box.getWidth());
                    box.setLayoutX(box.getLayoutX() - (box.getLayoutX() - mouseEvent.getX()));
                    break;
                case "rightSide":
                    double x = box.getLayoutX() + box.getWidth();
                    double mx = mouseEvent.getX();
                    double width = box.getWidth();
                    // 즐어들어야함
                    if (x > mx) {
                        box.setWidth(x - mx + width);
                    }
                    // 길어져야함
                    else {
                        box.setWidth(mx-x+width);
                    }
                    break;
                case "topSide":
                    box.setHeight(box.getHeight() + box.getLayoutY() - mouseEvent.getY());
                    box.setLayoutY(mouseEvent.getY());
                    break;
                case "bottomSide":
                    break;
                default:
                    break;

            }
        });

        newScene = new Scene(fullScreenPane, screen.getWidth(), screen.getHeight(), Color.TRANSPARENT);
        newScene.setRoot(fullScreenPane);
        stage.setScene(newScene);
        stage.setX(0);
        stage.setY(0);
    }

    // 오버레이 사각형 주의에 마우스가 오면 마우스 커서를 알맞은 화살표로 바꿔주는 메서드
    private void changeMouseCursor(Pane fullScreenPane, String pos) {
        if (pos.equals("default")) {
            fullScreenPane.setCursor(Cursor.DEFAULT);
            return;
        }
        if (pos.equals("leftSide") || pos.equals("rightSide")) {
            javafx.scene.image.Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/setting/mouseHorizontalArrow.png")).toString());
            fullScreenPane.setCursor(new ImageCursor(image, image.getWidth() / 2, image.getHeight() / 2));
            return;
        }
        if (pos.equals("topSide") || pos.equals("bottomSide")) {
            javafx.scene.image.Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/setting/mouseVerticalArrow.png")).toString());
            fullScreenPane.setCursor(new ImageCursor(image, image.getWidth() / 2, image.getHeight() / 2));
        }

    }

    // 마우스가 오버레이 박스의 어느 위치에 있는지 체크
    private String checkMousePos(double mouseX, double mouseY) {
        Bounds bounds = box.localToScene(box.getBoundsInLocal());
        double boxX = bounds.getMinX();
        double boxY = bounds.getMinY();

        double width = box.getWidth();
        double height = box.getHeight();

        double boundary = 20;

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
//        fullScreenPane = new Pane();
        box = new Rectangle(100, 100, Color.web("#000000", 0.01));
//        box.setOpacity(0.1);
        box.setStroke(Color.BLUE);
        box.setStrokeWidth(3.0);
        box.setLayoutX(500);
        box.setLayoutY(500);
        fullScreenPane.getChildren().add(box);

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

        // Mouse dragged event
        box.setOnMouseDragged(event -> {
            box.setTranslateX(event.getSceneX() - offsetX[0]);
            box.setTranslateY(event.getSceneY() - offsetY[0]);
            double a = event.getSceneX() - offsetX[0];
            double b = event.getSceneY() - offsetY[0];

//            gc.setFill(Color.web("#FF0000", 0));
//            gc.fillRect(a,b, 100, 100);

            gc.clearRect(a, b, 100, 100);
        });

    }
}

