package com.theperfectfit.theperfectfit;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        double WIDTH=600;
        double HEIGHT=600;
        OpeningScene openingScene = new OpeningScene(WIDTH,HEIGHT);
        Scene scene = openingScene.getOpeningScene();
        stage.setMinWidth(WIDTH);
        stage.setMinHeight(HEIGHT);
        stage.setScene(scene);
        stage.setTitle("The Perfect Fit");
        scene.getRoot().setOpacity(0);
        stage.show();
        stage.setResizable(false);
        fadeIn((AnchorPane) scene.getRoot());
    }
    public void fadeIn(AnchorPane root) {
        FadeTransition fadein = new FadeTransition();
        fadein.setDuration(Duration.millis(1000));
        fadein.setNode(root);
        fadein.setFromValue(0);
        fadein.setToValue(1);
        fadein.play();
    }

    public static void main(String[] args) {
        launch();
    }
}