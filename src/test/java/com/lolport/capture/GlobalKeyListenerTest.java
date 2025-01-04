package com.lolport.capture;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.sound.sampled.AudioSystem;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static org.junit.jupiter.api.Assertions.*;


class GlobalKeyListenerTest {
//    private static final Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
    private static final Logger logger = GlobalKeyListener.logger;
    static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    @BeforeEach
    public void startKeyHooking() throws NativeHookException {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outputStream));
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
    }
    @Test
    public void ctrlCheck(@TempDir Path tempDir) throws AWTException, IOException, NativeHookException {
        boolean result = false;
        Path logFile = tempDir.resolve("test.log");

        FileHandler fileHandler = new FileHandler(logFile.toString(), true);
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
        logger.setLevel(Level.INFO);

//        logger.info("작동됨");
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_PRINTSCREEN);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_PRINTSCREEN);
        GlobalScreen.unregisterNativeHook();

        File file = new File(logFile.toString());
//        logger.info(file.toPath().toString());
        fileHandler.flush();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String s;
        while((s = reader.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(s);
            while(st.hasMoreTokens()) {
                if(st.nextToken().equals("작동됨")){
                    result = true;
                    break;
                }
            }
        }
        reader.close();
        fileHandler.close();
//        assertEquals("CTRL + PRINTSCREEN", "");
        assertTrue(result);


//        assertEquals(1,1);
    }

}