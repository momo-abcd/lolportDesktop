package com.lolport.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageView uploadIcon = new ImageView(getClass().getResource("/images/icon/home.svg").toString()); // 파일 경로 지정

        Image homePng = new Image(getClass().getResource("/images/icon/homeWhite.png").toString());
        Image uploadPng = new Image(getClass().getResource("/images/icon/homeWhite.png").toString());
        Image galleryPng = new Image(getClass().getResource("/images/icon/homeWhite.png").toString());
        Image howPng = new Image(getClass().getResource("/images/icon/homeWhite.png").toString());
        Image settingPng = new Image(getClass().getResource("/images/icon/homeWhite.png").toString());

        homeImg.setImage(homePng);
        uploadImg.setImage(uploadPng);
        galleryImg.setImage(galleryPng);
        howImg.setImage(howPng);
        settingImg.setImage(settingPng);

        homeBtn.setGraphic(homeImg);
        uploadBtn.setGraphic(uploadImg);
        galleryBtn.setGraphic(galleryImg);
        howBtn.setGraphic(howImg);
        settingBtn.setGraphic(settingImg);

    }
}
