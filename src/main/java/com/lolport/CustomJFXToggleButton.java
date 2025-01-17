package com.lolport;

import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.skins.JFXToggleButtonSkin;

import javafx.scene.control.Skin;
import javafx.scene.shape.Circle;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.lang.reflect.Field;

public class CustomJFXToggleButton extends JFXToggleButton {

    public CustomJFXToggleButton() {
        super();
        this.getStylesheets().add(getClass().getResource("/css/navbar.css").toExternalForm());
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CustomJFXToggleButtonSkin(this);
    }

    public static class CustomJFXToggleButtonSkin extends JFXToggleButtonSkin {

        private Circle thumb;

        public CustomJFXToggleButtonSkin(JFXToggleButton control) {
            super(control);
            // Reflection으로 super()에서 생성된 Circle 가져오기
            try {
                Field thumbField = JFXToggleButtonSkin.class.getDeclaredField("circle");
                thumbField.setAccessible(true);
                this.thumb = (Circle) thumbField.get(this);
                // 가져온 Circle에 애니메이션 추가
                if (thumb != null) {
                    TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), thumb);
                    transition.setByX(10); // X축으로 이동
                    transition.setAutoReverse(true); // 되돌아오도록 설정
                    transition.setCycleCount(2); // 애니메이션 반복 횟수
                    transition.play();
                }

            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}