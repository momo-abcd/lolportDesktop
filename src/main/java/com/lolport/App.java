package com.lolport;

import com.lolport.capture.overlay.ChatOverlay;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.awt.*;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private double x, y;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = loadFXML("primary");

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        Font.getDefault().getName();
        Font.getFontNames();
        scene = new Scene(root);
        System.out.println(Font.loadFont(getClass().getResource("/fonts/GmarketSansTTFLight.ttf").toExternalForm(), 10).getName());
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("연습!");
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.sizeToScene();

        overlay(stage);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        Stage stage = (Stage) scene.getWindow();
        stage.sizeToScene();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private void overlay(Stage mainStage) {
        Button btn = new Button("오버레이 기능");
        Pane pane = new Pane();
        pane.getChildren().add(btn);
        Scene newScene = new Scene(pane, 500, 400);

        btn.setOnAction(actionEvent ->  {

            Pane p = new Pane();
//            p.setBackground(Background.fill(Color.LIGHTGRAY));
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Canvas canvas = new Canvas(screenSize.getWidth(), screenSize.getHeight());
            GraphicsContext gc = canvas.getGraphicsContext2D();
            ChatOverlay overlay = new ChatOverlay(gc);

            gc.setFill(Color.web("#808080", 0.2));
            gc.fillRect(0,0, screenSize.getWidth(), screenSize.getHeight());
            p.getChildren().add(canvas);
            p.setBackground(Background.fill(Color.web("#000000", 0)));
            p.setMouseTransparent(false);
            canvas.setMouseTransparent(false);
            p.getChildren().add(overlay);
            p.setPickOnBounds(true);
            Scene grayScreen = new Scene(p, screenSize.getWidth(), screenSize.getHeight(),Color.TRANSPARENT);
//            grayScreen.setFill(Color.RED);
//            mainStage.setOpacity(0.4);
//            mainStage.setOpacity(0);
            mainStage.setScene(grayScreen);
            mainStage.setX(0);
            mainStage.setY(0);


        });
        btn.fire();

        mainStage.setScene(newScene);
    }


    public static void main(String[] args) {
        launch();
    }

}