package com.lolport.capture;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.lolport.capture.overlay.ChatOverlayImpl;
import com.lolport.util.ConfigControl;
import javafx.scene.shape.Rectangle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        // 프린트스크린 키가 눌렸다면 알 맞은 캡쳐 메서드를 호출해줌
        if (e.getKeyCode() == NativeKeyEvent.VC_PRINTSCREEN) {
            if (!CaptureOnOff.CTRL_KEY) return;
            if (CaptureOnOff.Z) {
                captureScreen("chat");
                return;
            }
            if (CaptureOnOff.TAB) {
                captureScreen("tab");
                return;
            }
            captureScreen("full");
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) CaptureOnOff.CTRL_KEY = true;
        if (e.getKeyCode() == NativeKeyEvent.VC_Z) CaptureOnOff.Z = true;
        if (e.getKeyCode() == NativeKeyEvent.VC_TAB) CaptureOnOff.TAB = true;
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) CaptureOnOff.CTRL_KEY = false;
        if (e.getKeyCode() == NativeKeyEvent.VC_Z) CaptureOnOff.Z = false;
        if (e.getKeyCode() == NativeKeyEvent.VC_TAB) CaptureOnOff.TAB = false;
    }

    private void captureScreen(String type) {
        // 캡처 기능 설정이 꺼져 있으면 화면캡처 실행하지 않음
        if(!ConfigControl.isCaptureTypeOnOff(type)) return;

        // 사진을 생성할 폴더가 없을 시 폴더를 새로 만들어줌
        String userHome = System.getProperty("user.home");
        File captureFolder = new File(userHome, "/Pictures/lolport/" + type+ "/");
        if (!captureFolder.exists()) {
            captureFolder.mkdirs();
        }

        // 실제로 캡쳐 기능을 담당하는 부분
        // !!! 여기서 각 캡쳐별 상자의 크기, 위치 정보를 가져와서 알맞게 코드를 작성해야함
        try {
            Robot robot = new Robot();
//            BufferedImage screenCapture = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            Rectangle box = ChatOverlayImpl.box;
            BufferedImage screenCapture = robot.createScreenCapture(new java.awt.Rectangle(500, 350, 400, 200));
            String filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss"));
            File image = new File(captureFolder, filename + ".png");
            ImageIO.write(screenCapture, "png", image);
        } catch (AWTException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
