package com.lolport.capture;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class GlobalKeyListener implements NativeKeyListener {
    static Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());

//    @Override
//    public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
//        NativeKeyListener.super.nativeKeyTyped(nativeEvent);
//    }

    // 키가 눌렀을 때 이벤트 메서드
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (CaptureOnOff.CTRL_KEY) {
            if (e.getKeyCode() == NativeKeyEvent.VC_PRINTSCREEN) {
                logger.info("작동됨");
                String userHome = System.getProperty("user.home");
                File captureFolder = new File(userHome, "Pictures/lolport");
                if (!captureFolder.exists()) {
                    captureFolder.mkdirs();
                }

                try {
                    Robot robot = new Robot();
                    BufferedImage screenCapture = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

                    File image = new File(captureFolder, "screenshot.png");
                    ImageIO.write(screenCapture, "png", image);
                } catch (AWTException | IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL && !CaptureOnOff.CTRL_KEY) {
            CaptureOnOff.CTRL_KEY = true;
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            CaptureOnOff.CTRL_KEY = false;
        }
    }
}
