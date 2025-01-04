package com.lolport.capture;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.lang.annotation.Native;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalKeyListener implements NativeKeyListener {
    static Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
        NativeKeyListener.super.nativeKeyTyped(nativeEvent);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
//        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        if(CaptureOnOff.CTRL_KEY) {
            if(e.getKeyCode() == NativeKeyEvent.VC_PRINTSCREEN) {
                System.out.println("CTRL + PRINTSCREEN");
                logger.info("작동됨");
            }
        }
        if(e.getKeyCode() == NativeKeyEvent.VC_CONTROL && !CaptureOnOff.CTRL_KEY) {
            CaptureOnOff.CTRL_KEY=true;
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if(e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            CaptureOnOff.CTRL_KEY = false;
        }
    }
}
