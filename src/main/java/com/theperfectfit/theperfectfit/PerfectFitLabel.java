package com.theperfectfit.theperfectfit;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class PerfectFitLabel extends Label {


    public PerfectFitLabel(String text) {
        setText(text);
        setLabelFont();

    }
    public void setLabelFont() {
        try {
            setFont(Font.loadFont("file:src/main/resources/heavy_data.ttf", 30));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
