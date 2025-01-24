package com.lolport.custom;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.skins.JFXToggleButtonSkin;
import com.lolport.capture.GlobalKeyListener;
import javafx.animation.*;
import javafx.scene.control.Skin;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CustomJFXToggleButton extends JFXToggleButton {
    static Logger logger = Logger.getLogger(CustomJFXToggleButton.class.getPackage().getName());

    SequentialTransition sq = new SequentialTransition();

    GlobalKeyListener globalKeyListener;


    public CustomJFXToggleButton() {
        super();
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/navbar.css")).toExternalForm());
        this.selectedProperty().addListener((observableValue, prev, now) -> {
            // 토글을 켰다면
            if (now) {
                globalKeyListener = new GlobalKeyListener();
                try {
                    GlobalScreen.registerNativeHook();
                } catch (NativeHookException e) {
                    System.err.println("There was a problem registering the native hook.");
                    System.err.println(e.getMessage());
                }
                GlobalScreen.addNativeKeyListener(globalKeyListener);
            }
            else {
                try {
                    GlobalScreen.removeNativeKeyListener(globalKeyListener);
                    GlobalScreen.unregisterNativeHook();
                    GlobalScreen.setEventDispatcher(null);
                } catch (NativeHookException e) {
                    System.err.println("There was a problem registering the native hook.");
                    System.err.println(e.getMessage());
                }
            }
        });
    }

    // 동그라미에 애니메이션 효과 주기 위한 Event 처리 메서드
    @Override
    public void fire() {
        super.fire();
        sq = new SequentialTransition();
        if (super.selectedProperty().get()) {

            Circle thumb = getCircleNode();

            DropShadow dropShadow = new DropShadow(BlurType.GAUSSIAN, Color.rgb(173, 0, 0, .7), 0, 0, 0, 0);
            thumb.setEffect(dropShadow);

            Timeline firstTimeline = new Timeline();
            Timeline secondTimeline = new Timeline();
            Timeline thirdTimeline = new Timeline();

            KeyValue spreadKeyValue = new KeyValue(dropShadow.spreadProperty(), .9, Interpolator.LINEAR);
            KeyValue colorKeyValue = new KeyValue(dropShadow.colorProperty(), Color.rgb(173, 0, 0, 0), Interpolator.LINEAR);
            KeyValue radiusKeyValue = new KeyValue(dropShadow.radiusProperty(), 25);

            firstTimeline.setCycleCount(1);
            secondTimeline.setCycleCount(1);
            thirdTimeline.setCycleCount(1);

            firstTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), spreadKeyValue, radiusKeyValue));
            secondTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), colorKeyValue));


            sq.getChildren().addAll(firstTimeline, secondTimeline);
            sq.setCycleCount(1);
            sq.setOnFinished(actionEvent -> {
                sq.play();
            });
            sq.setDelay(Duration.millis(400));
            sq.play();
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
                thumb = (Circle) thumbField.get(this);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.err.println(e.toString());
            }
        }
    }
}