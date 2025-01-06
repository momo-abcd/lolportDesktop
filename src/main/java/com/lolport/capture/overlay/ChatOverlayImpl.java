package com.lolport.capture.overlay;

import com.lolport.capture.OverLay;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;

import static java.awt.Toolkit.getDefaultToolkit;

public class ChatOverlayImpl implements OverLay {
    private Canvas canvas;
    private GraphicsContext gc;

    private Pane fullScreenPane;

    private final Stage stage;
    private final Scene scene;

    public ChatOverlayImpl(Stage stage, Scene settingScene, Node contentSide) {
        this.stage = stage;
        this.scene = settingScene;
        // 기존 프로그램들은 투명화하여 보이지 않게 한다. 단, 이 클래스가 종료 될 때 원상 복구 해줘야함 -> key리스너 이용
        settingScene.setFill(Color.TRANSPARENT);
        contentSide.setOpacity(0);

        Pane p = new Pane();
        Scene newScene = new Scene(p);
        stage.setScene(newScene);
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
        scene.setRoot(fullScreenPane);
    }

    @Override
    public void removeTransGray() {

    }

    @Override
    // 여기서 생성한 fullScreenPane을 Scene에 붙여야함
    public void drawOverlayBox() {
//        fullScreenPane = new Pane();
        Rectangle box = new Rectangle(100, 100, javafx.scene.paint.Color.WHITE);
        box.setOpacity(0.1);
        box.setStroke(Color.BLUE);
        fullScreenPane.getChildren().add(box);

        // Initial offsets for dragging
        final double[] offsetX = {0};
        final double[] offsetY = {0};

        // Mouse pressed event
        box.setOnMousePressed(event -> {
            offsetX[0] = event.getSceneX() - box.getTranslateX();
            offsetY[0] = event.getSceneY() - box.getTranslateY();
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

