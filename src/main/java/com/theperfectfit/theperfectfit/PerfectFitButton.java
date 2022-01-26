package com.theperfectfit.theperfectfit;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class PerfectFitButton extends Button {
    String BUTTON_STYLE="-fx-background-color:black; -fx-text-fill:white;";
    String BUTTON_PRESSED_STYLE="-fx-background-color:black; -fx-text-fill:rgb(210, 209, 209);";
    public PerfectFitButton(String text) {
        setText(text);
        setButtonFont();
        setStyle(BUTTON_STYLE);

    }
    public void setButtonFont() {
        try {
            setFont(Font.loadFont("file:src/main/resources/heavy_data.ttf", 20));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void hover() {
        setStyle(BUTTON_PRESSED_STYLE);
    }
    public void unhover() {
        setStyle(BUTTON_STYLE);
    }

}
