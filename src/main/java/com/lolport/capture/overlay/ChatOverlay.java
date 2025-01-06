package com.lolport.capture.overlay;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ChatOverlay extends Pane {
    public ChatOverlay(GraphicsContext gc) {
        super();
        setPickOnBounds(false);

        Rectangle box = new Rectangle(100, 100, Color.WHITE);
        box.setOpacity(0.1);
        box.setStroke(Color.BLUE);
        getChildren().add(box);

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

            gc.clearRect(a,b, 100, 100);

        });
    }
}
