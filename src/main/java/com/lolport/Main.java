package com.lolport;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

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

    public static Parent loadFXML(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("/fxml/" + fxml + ".fxml")));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            System.err.println(e.toString());
        }
        return null; // 이렇게 null을 해줘도 되는게 맞나...? 어차피 안쓰일 코드인데 흠..
    }


    public static void main(String[] args) {
        launch();
    }

}