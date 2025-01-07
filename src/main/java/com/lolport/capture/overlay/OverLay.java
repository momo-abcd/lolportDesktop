package com.lolport.capture.overlay;

public interface OverLay {
    void drawTransGray(); // 전체화면을 회색 반투명으로 색칠하는 기능
    void removeTransGray(); // 오버레이 사각형 안의 반투명 회색을 맑게 해주는 기능
    void drawOverlayBox();
}
