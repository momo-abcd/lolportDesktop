package com.lolport.capture;

import com.lolport.capture.overlay.ChatOverlay;
import com.lolport.capture.overlay.ChatOverlayImpl;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class SettingController {
    private final Stage stage; // 블루레이 사각형 설정시 프로그램 투명화를 위해 필요
    private final Scene settingScene;
    private final VBox contentSide; // 프로그램의 오른쪽 화면에대한 노드 (dock 사이드, 컨탠츠 사이드 두 개 존재)

    public SettingController(Stage stage, Scene settingScene) {
        this.stage = stage;
        this.settingScene = settingScene;
        this.contentSide = new VBox(); // 이 요소에 설정탭에 관련된 UI들을 배치하는거임

        Button btn1 = new Button("채팅창오버레이 설정");
        Button btn2 = new Button("탭창오버레이 설정");
        Button btn3 = new Button("전체화면오버레이 설정");
        this.contentSide.getChildren().addAll(btn1, btn2, btn3);

        btn1.setOnAction(actionEvent -> {
            OverLay chatOverLay = new ChatOverlayImpl(stage, settingScene, contentSide);
            System.out.println("눌림");
        });
        btn2.setOnAction(actionEvent -> {

        });
        btn3.setOnAction(actionEvent -> {

        });
        // SCENE 전환
        settingScene.setRoot(this.contentSide );
    }
}
