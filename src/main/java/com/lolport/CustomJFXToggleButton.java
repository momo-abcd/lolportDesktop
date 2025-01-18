package com.lolport;

import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.skins.JFXToggleButtonSkin;

import javafx.animation.*;
import javafx.scene.control.Skin;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.lang.reflect.Field;


public class CustomJFXToggleButton extends JFXToggleButton {

    Timeline t1 = new Timeline();
    SequentialTransition sq = new SequentialTransition();


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

//        DropShadow dropShadow = new DropShadow(BlurType.THREE_PASS_BOX, Color.rgb(173, 0,0,.3), 40, 0.9, 0, 0);


        // 버튼이 열리는 경우 애니메이션 실행
        // true가 열린 상태이겠지..?
//        Timeline t1 = new Timeline();
        Timeline t2 = new Timeline();
        if (super.selectedProperty().get()) {

            Circle thumb = getCircleNode();

            DropShadow dropShadow = new DropShadow(BlurType.GAUSSIAN, Color.rgb(173, 0, 0, .4), 25, 0, 0, 0);
            thumb.setEffect(dropShadow);

            Timeline firstTimeline = new Timeline();
            Timeline secondTimeline = new Timeline();
            Timeline thirdTimeline = new Timeline();

            KeyValue spreadKeyValue = new KeyValue(dropShadow.spreadProperty(), .9);
            KeyValue colorKeyValue = new KeyValue(dropShadow.colorProperty(), Color.rgb(173, 0, 0, 0));

            firstTimeline.setCycleCount(1);
            secondTimeline.setCycleCount(1);
            thirdTimeline.setCycleCount(1);

            firstTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(975), spreadKeyValue));
            secondTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), colorKeyValue));

            sq.getChildren().addAll(firstTimeline, secondTimeline);
            sq.setCycleCount(Animation.INDEFINITE);
            sq.setDelay(Duration.millis(400));
            sq.play();

//
////            KeyValue kv3 = new KeyValue(dropShadow1.colorProperty(), Color.rgb(13, 0,0,0), Interpolator.LINEAR);
//            KeyFrame kf2 = new KeyFrame(Duration.millis(375), kv3);
//            t2.getKeyFrames().add(kf2);
//            KeyValue kv4 = new KeyValue(dropShadow1.colorProperty(), Color.BLUEVIOLET, Interpolator.LINEAR);
//            KeyFrame kf3 = new KeyFrame(Duration.millis(3000), kv4);
//            Timeline t3 = new Timeline(kf3);

//            SequentialTransition sq = new SequentialTransition(t1,t2);
//            sq.play();

        }
        // 버튼이 닫히는 경우 애니메이션 종료
        else {
            sq.stop();
        }
    }

    private Circle getCircleNode() {
        return CustomJFXToggleButtonSkin.thumb;
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