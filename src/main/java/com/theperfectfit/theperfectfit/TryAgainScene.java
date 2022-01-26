package com.theperfectfit.theperfectfit;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;

public class TryAgainScene {

    Stage stage;
    Scene tryAgainScene;
    ImageView Background;
    PerfectFitButton tryAgain;
    PerfectFitButton mainMenu;
    AnchorPane root;
    double WIDTH;
    double HEIGHT;
    PerfectFitLabel score;
    PerfectFitLabel scoreTitle;
    PerfectFitLabel HighScoreTitle;
    PerfectFitLabel HighScore;
    TranslateTransition move;
    ChangeListener<Number> adapter;
    public void createTryAgainScene(double WIDTH, double HEIGHT, PerfectFitLabel score, Stage stage) {
        this.stage=stage;
        this.WIDTH=WIDTH;
        this.HEIGHT=HEIGHT;
        this.score=score;
        createBackground();
        createTryAgainButton();
        createMainMenuButton();
        createScore();
        createHighScore();
        tryAgainScene = new Scene(root,WIDTH,HEIGHT);
    }
    public Scene getScene() {
        return tryAgainScene;
    }

    public void createBackground() {
        root=new AnchorPane();
        try {
            Background = new ImageView(new Image(new FileInputStream("src/main/resources/TAIMAGE.jpeg")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Background.setFitHeight(HEIGHT*2);
        Background.setFitWidth(WIDTH + (WIDTH/10));
        Background.setY(0 - (HEIGHT/2));
        move = new TranslateTransition();
        move.setNode(Background);
        move.setByY(HEIGHT/2);
        move.setDuration(Duration.millis(8000));
        move.setCycleCount(Animation.INDEFINITE);
        move.setAutoReverse(true);
        move.play();
        root.getChildren().add(Background);
    }
    public void createScore() {
        scoreTitle = new PerfectFitLabel("SCORE");
        scoreTitle.setLayoutX(WIDTH/2 - (WIDTH/16));
        score.setLayoutX(WIDTH/2 - (WIDTH/50));
        scoreTitle.setLayoutY(HEIGHT/4);
        score.setLayoutY(scoreTitle.getLayoutY()+30);
        root.getChildren().add(scoreTitle);
        root.getChildren().add(score);

    }
    public void createHighScore() {

        HighScoreTitle = new PerfectFitLabel("HIGH SCORE");
        HighScore = new PerfectFitLabel(getHighScore());
        HighScoreTitle.setLayoutY(score.getLayoutY()+30);
        HighScoreTitle.setLayoutX((WIDTH/2)-(WIDTH/8));
        HighScore.setLayoutY(HighScoreTitle.getLayoutY()+30);
        HighScore.setLayoutX(WIDTH/2 - (WIDTH/50));
        root.getChildren().add(HighScoreTitle);
        root.getChildren().add(HighScore);

    }
    public static String getHighScore() {
        BufferedReader reader;
        String HighScore="";
        try {
            reader = new BufferedReader(new FileReader("src/main/resources/HighScore.txt"));
            HighScore = reader.readLine();

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return HighScore;

    }
    public void createTryAgainButton() {
        tryAgain = new PerfectFitButton("TRY AGAIN");
        tryAgain.setPrefWidth(WIDTH/4);
        tryAgain.setLayoutX((WIDTH/2)-(WIDTH/8));
        tryAgain.setLayoutY(HEIGHT/2);
        tryAgain.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                tryAgain.hover();
            }
        });
        tryAgain.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                tryAgain.unhover();
            }
        });
        tryAgain.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                tryAgain.setOnAction(null);
                fadeOut(root,1);

            }

        });
        root.getChildren().add(tryAgain);

    }
    public void switchToGame() {

        GameScene gameScene = new GameScene();
        gameScene.createGameScene(WIDTH,HEIGHT,stage);
        Scene game = gameScene.getGameScene();
        game.getRoot().setOpacity(0);
        stage.setScene(game);
        fadeIn((AnchorPane)game.getRoot());


    }
    public void fadeIn(AnchorPane root) {
        FadeTransition fadein = new FadeTransition();
        fadein.setDuration(Duration.millis(1000));
        fadein.setNode(root);
        fadein.setFromValue(0);
        fadein.setToValue(1);
        fadein.play();
    }
    public void fadeOut(AnchorPane root, int importance) {

        FadeTransition fadeout = new FadeTransition();
        fadeout.setDuration(Duration.millis(2000));
        fadeout.setNode(root);
        fadeout.setFromValue(1);
        fadeout.setToValue(0);
        fadeout.play();
        fadeout.setOnFinished((ActionEvent e)->{

            if(importance==1) {
                switchToGame();
            }
            else {
                switchToMainMenu();
            }


        });
    }
    public void createMainMenuButton() {
        mainMenu = new PerfectFitButton("MAIN MENU");
        mainMenu.setPrefWidth(WIDTH/4);
        mainMenu.setLayoutX((WIDTH/2)-(WIDTH/8));
        mainMenu.setLayoutY(tryAgain.getLayoutY()+50);
        mainMenu.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                mainMenu.hover();
            }
        });
        mainMenu.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                mainMenu.unhover();
            }
        });
        mainMenu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                mainMenu.setOnAction(null);
                fadeOut(root,2);
            }

        });
        root.getChildren().add(mainMenu);

    }
    public void switchToMainMenu() {
        OpeningScene openingScene = new OpeningScene(WIDTH,HEIGHT);
        Scene scene = openingScene.getOpeningScene();
        scene.getRoot().setOpacity(0);
        stage.setScene(scene);
        fadeIn((AnchorPane) scene.getRoot());
    }


}
