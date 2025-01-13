package com.lolport.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.spreadsheet.Grid;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class NavBarController implements Initializable {
    @FXML
    private Button homeBtn;
    @FXML
    private Button uploadBtn;
    @FXML
    private Button galleryBtn;
    @FXML
    private Button howBtn;
    @FXML
    private Button settingBtn;
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
    private void handleNavStatus(ActionEvent e) {
        Button currentBtn =  (Button)e.getSource();
        String iconPng="";
        switch (currentBtn.getId()) {
            case "homeBtn":
                iconPng = "homeWhite";
                break;
            case "uploadBtn":
                iconPng = "uploadWhite";
                break;
            case "galleryBtn":
                iconPng = "galleryWhite";
                break;
            case "howBtn":
                iconPng = "howWhite";
                break;
            case "settingBtn":
                iconPng = "settingWhite";
                break;
            default:
                break;
        }
        if(currentBtn.isFocused()) {
            currentBtn.getStyleClass().remove("notfocused");
            Image img = iconMap.get(iconPng);
            ((ImageView)currentBtn.graphicProperty().get()).setImage(img);
        }
    }

    private Map<String, Image> iconMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        ImageView uploadIcon = new ImageView(getClass().getResource("/images/icon/home.svg").toString()); // 파일 경로 지정
        Image homeWhite = new Image(getClass().getResource("/images/icon/homeWhite.png").toString());
        Image uploadWhite = new Image(getClass().getResource("/images/icon/uploadWhite.png").toString());
        Image galleryWhite = new Image(getClass().getResource("/images/icon/galleryWhite.png").toString());
        Image howWhite = new Image(getClass().getResource("/images/icon/howWhite.png").toString());
        Image settingWhite = new Image(getClass().getResource("/images/icon/settingWhite.png").toString());

        Image homeBlack = new Image(getClass().getResource("/images/icon/homeBlack.png").toString());
        Image uploadBlack = new Image(getClass().getResource("/images/icon/uploadBlack.png").toString());
        Image galleryBlack = new Image(getClass().getResource("/images/icon/galleryBlack.png").toString());
        Image howBlack = new Image(getClass().getResource("/images/icon/howBlack.png").toString());
        Image settingBlack = new Image(getClass().getResource("/images/icon/settingBlack.png").toString());

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
        String icon = "url(\"" + getClass().getResource("/images/icon/homeBlack.png") + "\")";
        homeImg.setStyle("-fx-image:" + icon);

        homeBtn.setOnAction(this::handleNavStatus);
//        Platform.runLater(() -> {
//            homeBtn.requestFocus();
//            navGrid.getChildren().forEach(navBtn -> {
//                if (!navBtn.isFocused()) {
//                    navBtn.getStyleClass().add("notfocused");
//                }
//            });
//        });

        navGrid.getChildren().forEach(navBtn -> {
            if(!navBtn.isFocused()) {
                navBtn.getStyleClass().add("notfocused");
                navBtn.focusedProperty().addListener(((observableValue, aBoolean, newValue) -> handleFocusChange((Button)navBtn, newValue)));
            }
        });
    }

    private void handleFocusChange(Button currentBtn, Boolean isFocused) {
        if(isFocused) {
            String iconPng="";
            switch (currentBtn.getId()) {
                case "homeBtn":
                    iconPng = "homeWhite";
                    break;
                case "uploadBtn":
                    iconPng = "uploadWhite";
                    break;
                case "galleryBtn":
                    iconPng = "galleryWhite";
                    break;
                case "howBtn":
                    iconPng = "howWhite";
                    break;
                case "settingBtn":
                    iconPng = "settingWhite";
                    break;
                default:
                    break;
            }
                currentBtn.getStyleClass().remove("notfocused");
                Image img = iconMap.get(iconPng);
                ((ImageView)currentBtn.graphicProperty().get()).setImage(img);

        }
    }
}
