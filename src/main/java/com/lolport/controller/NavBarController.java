package com.lolport.controller;

import com.lolport.custom.CustomJFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class NavBarController implements Initializable {
    @FXML
    private Button homeBtn;
    @FXML
    private ImageView homeImg;
    @FXML
    private ImageView uploadImg;
    @FXML
    private ImageView galleryImg;
    @FXML
    private ImageView howImg;
    @FXML
    private ImageView settingImg;
    @FXML
    private GridPane navGrid;

    @FXML
    private CustomJFXToggleButton toggleBtn;

    private final Map<String, Image> iconMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image homeWhite = new Image(Objects.requireNonNull(getClass().getResource("/images/icon/homeWhite.png")).toExternalForm());
        Image uploadWhite = new Image(Objects.requireNonNull(getClass().getResource("/images/icon/uploadWhite.png")).toExternalForm());
        Image galleryWhite = new Image(Objects.requireNonNull(getClass().getResource("/images/icon/galleryWhite.png")).toExternalForm());
        Image howWhite = new Image(Objects.requireNonNull(getClass().getResource("/images/icon/howWhite.png")).toExternalForm());
        Image settingWhite = new Image(Objects.requireNonNull(getClass().getResource("/images/icon/settingWhite.png")).toExternalForm());

        Image homeBlack = new Image(Objects.requireNonNull(getClass().getResource("/images/icon/homeBlack.png")).toExternalForm());
        Image uploadBlack = new Image(Objects.requireNonNull(getClass().getResource("/images/icon/uploadBlack.png")).toExternalForm());
        Image galleryBlack = new Image(Objects.requireNonNull(getClass().getResource("/images/icon/galleryBlack.png")).toExternalForm());
        Image howBlack = new Image(Objects.requireNonNull(getClass().getResource("/images/icon/howBlack.png")).toExternalForm());
        Image settingBlack = new Image(Objects.requireNonNull(getClass().getResource("/images/icon/settingBlack.png")).toExternalForm());

        iconMap.put("homeWhite", homeWhite);
        iconMap.put("uploadWhite", uploadWhite);
        iconMap.put("galleryWhite", galleryWhite);
        iconMap.put("howWhite", howWhite);
        iconMap.put("settingWhite", settingWhite);

        iconMap.put("homeBlack", homeBlack);
        iconMap.put("uploadBlack", uploadBlack);
        iconMap.put("galleryBlack", galleryBlack);
        iconMap.put("howBlack", howBlack);
        iconMap.put("settingBlack", settingBlack);

        homeImg.setImage(homeBlack);
        uploadImg.setImage(uploadBlack);
        galleryImg.setImage(galleryBlack);
        howImg.setImage(howBlack);
        settingImg.setImage(settingBlack);

        // 토글버튼 녹화 애니메이션
        toggleBtn.lookup(".thumb");


        // 맨 처음 메인페이지가 포커싱 되었을 때 event 발생 시킴
        homeBtn.focusedProperty().addListener((observableValue, aBoolean, newBoolean) -> {
            // 지금 포커스가 된 상태라면
            if (newBoolean) {
                homeBtn.fire();
            }
        });
    }


    @FXML
    public void mainPageHandler(ActionEvent e) {
        navBtnClickEventHandler((Button) e.getSource());
        Button source = (Button) e.getSource();
        Parent root = source.getScene().getRoot();
        root.getChildrenUnmodifiable().forEach(node -> {
            if (node.idProperty().get().equals("contentBox")) {
                Text text = new Text("메인페이지입니다.");
                text.setLayoutX(200);
                text.setLayoutY(200);

                ((Pane) node).getChildren().addAll(text);
            }
        });
    }

    @FXML
    public void uploadHandler(ActionEvent e) {
        navBtnClickEventHandler((Button) e.getSource());
    }

    @FXML
    public void galleryHandler(ActionEvent e) {
        navBtnClickEventHandler((Button) e.getSource());

    }

    @FXML
    public void howHandler(ActionEvent e) {
        navBtnClickEventHandler((Button) e.getSource());

    }

    @FXML
    public void settingHandler(ActionEvent e) {
        navBtnClickEventHandler((Button) e.getSource());
    }

    private void navBtnClickEventHandler(Button btn) {
        changeNavToWhite(btn);
        changeNavToBlack();
    }

    private void changeNavToWhite(Button btn) {
        // 해당 네비게이션이 클릭 되면 클릭 되었다는 효과를 줘야함( 선택 되었다는 효과)
        // 즉, 글자를 흰색으로, 아이콘을 흰색으로 바꿔줘야함
        Image homeWhite = iconMap.get(findBlackOrWhiteImg(btn.getId(), "White"));
        // toggle용으로 remove를 해주고 add 함수를 이용해 focused를 추가해줌
        btn.getStyleClass().remove("focused");
        btn.getStyleClass().add("focused");
        ((ImageView) btn.getGraphic()).setImage(homeWhite);
    }

    // 다른 네비게이션들은 모두 흑백으로 바꿔준다.
    private void changeNavToBlack() {
        navGrid.getChildren().forEach(node -> {
            Button anotherBtn = (Button) node;
            if (!anotherBtn.isFocused()) {
                anotherBtn.getStyleClass().remove("focused");
                ((ImageView) anotherBtn.getGraphic()).setImage(iconMap.get(findBlackOrWhiteImg(anotherBtn.getId(), "Black")));
            }
        });
    }

    // 버튼의 ID 값으로 어떤 검정아이콘 이미지를 꺼내야하는지 구해주는 함수
    // iconMap에 들어갈 파라미터를 구하는 함수임
    private String findBlackOrWhiteImg(String id, String color) { // color는 Black or White 값
        String iconPng = "";
        switch (id) {
            case "homeBtn":
                iconPng = "home";
                break;
            case "uploadBtn":
                iconPng = "upload";
                break;
            case "galleryBtn":
                iconPng = "gallery";
                break;
            case "howBtn":
                iconPng = "how";
                break;
            case "settingBtn":
                iconPng = "setting";
                break;
            default:
                break;
        }
        return iconPng + color;
    }

}
