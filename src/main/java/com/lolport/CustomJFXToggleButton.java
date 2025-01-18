package com.lolport;

import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.skins.JFXToggleButtonSkin;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Skin;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.shape.Circle;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.lang.reflect.Field;

public class CustomJFXToggleButton extends JFXToggleButton {

    Timeline timeline = new Timeline();

    public CustomJFXToggleButton() {
        super();
        this.getStylesheets().add(getClass().getResource("/css/navbar.css").toExternalForm());
    }

    // 동그라미에 애니메이션 효과 주기 위한 Event 처리 메서드
    @Override
    public void fire() {
        super.fire();

        // 동그라미 애니메이션 관련 코드
        // 리플랙션으로 동그라미 Node 가지고 옴
//        Circle thumb = CustomJFXToggleButtonSkin.thumb;
//        Field thumbField = null;
//        try {
//            thumbField = JFXToggleButtonSkin.class.getDeclaredField("circle");
//            thumbField.setAccessible(true);
//            thumb = (Circle) thumbField.get(this);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
        
        // 버튼이 열리는 경우 애니메이션 실행
        // true가 열린 상태이겠지..?
        if(super.selectedProperty().get()) {
//            Timeline timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.setAutoReverse(true);
            String css = "-fx-effect:dropshadow( two-pass-box ,  rgba(173,0,0,.3), 40, 0.9, 0, 0)";
            DropShadow dropShadow = new DropShadow();


            KeyValue kv = new KeyValue(CustomJFXToggleButtonSkin.thumb.styleProperty(), css);
            KeyFrame kf = new KeyFrame(Duration.millis(1500), kv);
            timeline.getKeyFrames().add(kf);
            timeline.play();
            
        }
        // 버튼이 닫히는 경우 애니메이션 종료
        else {
            timeline.stop();
        }
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CustomJFXToggleButtonSkin(this);
    }

    public static class CustomJFXToggleButtonSkin extends JFXToggleButtonSkin {

        static Circle thumb;

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