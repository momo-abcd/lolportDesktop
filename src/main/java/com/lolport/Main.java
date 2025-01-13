package com.lolport;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * JavaFX App
 */
public class Main extends Application {

    private double x, y;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // 글자 깨지는 현상 없애는 코드
        System.setProperty("prism.lcdtext", "false");
        // svg 라이브러리

        Parent root = loadFXML("Main");

//        Font.getDefault().getName();
//        Font.getFontNames();
        scene = new Scene(root, Color.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("연습!");
//         메뉴창 없애는 코드 ( 나중에 주석 풀어야함 )
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.sizeToScene();
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        Stage stage = (Stage) scene.getWindow();
        stage.sizeToScene();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/"+fxml+".fxml"));

        return fxmlLoader.load();
    }




    public static void main(String[] args) {
        launch();
    }

}