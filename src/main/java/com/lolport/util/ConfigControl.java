package com.lolport.util;

import java.io.*;
import java.net.URISyntaxException;

public class ConfigControl {
    // 각 캡처 기능의 설정 값을 읽어오는 메서드
    public static boolean isCaptureTypeOnOff(String type) {
        try {
            File config = new File(ConfigControl.class.getResource("/.lolport.conf").toURI());
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(config)));
            String curConfig = "";
            while ((curConfig = br.readLine()) != null) {
                String[] input = curConfig.split("=");
                if (input[0].equals(type + "screencapture")) {
                    return input[1].equals("true");
                }
            }
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
