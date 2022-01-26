package com.theperfectfit.theperfectfit;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;

public class OpeningScene {
    Stage stage;
    Scene openingScene;
    AnchorPane root;
    Image lg;
    ImageView logo;
    ImageView Background;
    ImageView SubBackground;
    ImageView image1;
    ImageView image2;
    PerfectFitLabel tutorial1;
    PerfectFitLabel tutorial2;
    PerfectFitButton playButton;
    PerfectFitButton howToPlayButton;
    PerfectFitButton settingsButton;
    PerfectFitButton quitButton;
    PerfectFitButton nextButton;
    PerfectFitLabel HighScoreTitle;
    PerfectFitLabel HighScore;
    PerfectFitLabel credits;
    ScaleTransition scale;
    FadeTransition fadeone;
    FadeTransition fadetwo;
    FadeTransition fadethree;
    PerfectFitButton exit;
    TranslateTransition move;
    double WIDTH=600;
    double HEIGHT=600;

    public OpeningScene(double w, double h) {
        WIDTH=w;
        HEIGHT=h;
        createBackground();
        createLogo();
        createHighScore();
        createPlayButton();
        createHowToPlayButton();
        createQuitButton();

        openingScene = new Scene(root,w,h);
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
    public void createLogo() {

        try {
            lg = new Image(new FileInputStream("src/main/resources/TITLE.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        logo = new ImageView(lg);
        logo.setFitWidth(WIDTH/2);
        logo.setFitHeight(HEIGHT/2);
        logo.setPreserveRatio(true);
        logo.setX((WIDTH/2)-(WIDTH/4));
        logo.setY(10);

        credits = new PerfectFitLabel("By Christian Okey-Ezeh");
        credits.setLayoutX(WIDTH - (WIDTH/3.5));
        credits.setLayoutY(HEIGHT - 30);
        credits.setFont(Font.loadFont("file:src/main/resources/heavy_data.ttf", 15));
        root.getChildren().add(logo);
        root.getChildren().add(credits);
    }
    public void createHighScore() {

        HighScoreTitle = new PerfectFitLabel("HIGH SCORE");
        HighScore = new PerfectFitLabel(getHighScore());
        HighScoreTitle.setLayoutY(HEIGHT/4);
        HighScoreTitle.setLayoutX((WIDTH/2)-(WIDTH/8));
        HighScore.setLayoutY(HEIGHT/4 + (50));
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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }

        return HighScore;

    }
    public void createPlayButton() {
        playButton = new PerfectFitButton("PLAY");
        playButton.setPrefWidth(WIDTH/6);
        playButton.setLayoutX((WIDTH/2)-(WIDTH/12));
        playButton.setLayoutY(HEIGHT/2);
        playButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                playButton.hover();
            }
        });
        playButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                playButton.unhover();
            }
        });
        playButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                fadeOut(root);
                playButton.setOnAction(null);
            }

        });
        root.getChildren().add(playButton);

    }
    public void createHowToPlayButton() {
        howToPlayButton = new PerfectFitButton("HOW TO PLAY");
        howToPlayButton.setPrefWidth(WIDTH/4);
        howToPlayButton.setLayoutX((WIDTH/2)-(WIDTH/8));
        howToPlayButton.setLayoutY(playButton.getLayoutY()+50);
        howToPlayButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                howToPlayButton.hover();
            }
        });
        howToPlayButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                howToPlayButton.unhover();

            }
        });
        howToPlayButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                howToPlayButton.setOnAction(null);
                howToPlaySubscene();
            }

        });

        root.getChildren().add(howToPlayButton);
    }

    public void createQuitButton() {
        quitButton = new PerfectFitButton("QUIT");
        quitButton.setPrefWidth(WIDTH/6);
        quitButton.setLayoutX((WIDTH/2)-(WIDTH/12));
        quitButton.setLayoutY(howToPlayButton.getLayoutY()+50);
        quitButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                quitButton.hover();
            }
        });
        quitButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                quitButton.unhover();
            }
        });
        quitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                quitButton.setOnAction(null);
                Stage stage = (Stage) openingScene.getWindow();
                stage.close();
            }

        });
        root.getChildren().add(quitButton);

    }

    public void fadeOut(AnchorPane root) {
        FadeTransition fadeout = new FadeTransition();
        fadeout.setDuration(Duration.millis(1000));
        fadeout.setNode(root);
        fadeout.setFromValue(1);
        fadeout.setToValue(0);
        fadeout.play();
        fadeout.setOnFinished((ActionEvent e)->{
            switchToGame();
        });
    }
    public void fadeIn(AnchorPane root) {
        FadeTransition fadein = new FadeTransition();
        fadein.setDuration(Duration.millis(1000));
        fadein.setNode(root);
        fadein.setFromValue(0);
        fadein.setToValue(1);
        fadein.play();

    }
    public void switchToGame() {
        stage = (Stage) openingScene.getWindow();
        GameScene gameScene = new GameScene();
        gameScene.createGameScene(WIDTH,HEIGHT,stage);
        Scene game = gameScene.getGameScene();
        game.getRoot().setOpacity(0);
        stage.setScene(game);
        fadeIn((AnchorPane)game.getRoot());


    }
    public void howToPlaySubscene() {
        try {
            SubBackground = new ImageView(new Image(new FileInputStream("src/main/resources/SUBSCENEBACKGROUND.jpeg")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SubBackground.setFitHeight(HEIGHT/1.5);
        SubBackground.setFitWidth(WIDTH/1.5);
        SubBackground.setX(WIDTH/6);
        SubBackground.setY(HEIGHT/6);
        scale = new ScaleTransition();
        scale.setNode(SubBackground);
        scale.setByY(.2);
        scale.setByX(.2);
        scale.setDuration(Duration.millis(1000));



        fadeone = new FadeTransition();
        fadeone.setNode(SubBackground);
        fadeone.setFromValue(0);
        fadeone.setDuration(Duration.millis(1000));
        fadeone.setToValue(1);
        fadetwo = new FadeTransition();
        fadetwo.setNode(Background);
        fadetwo.setFromValue(1);
        fadetwo.setToValue(.3);
        fadethree = new FadeTransition();
        fadethree.setNode(logo);
        fadethree.setFromValue(1);
        fadethree.setToValue(.3);

        fadeone.play();

        fadeone.setOnFinished(e->{

            fadetwo.play();
            fadethree.play();
            scale.play();
        });
        scale.setOnFinished(e->{
            subscene();
        });

        root.getChildren().add(SubBackground);

    }

    public void subscene() {
        exitButton();
        nextButton();
        subsceneImages();
        subsceneText();
    }
    public void exitButton() {
        exit = new PerfectFitButton("X");
        exit.setLayoutX(SubBackground.getX());
        exit.setLayoutY(SubBackground.getY());
        exit.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                quitButton.hover();
            }
        });
        exit.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                quitButton.unhover();
            }
        });
        exit.setOnAction(e->{
            removeSubscene();

        });
        root.getChildren().add(exit);

    }
    public void nextButton() {
        nextButton = new PerfectFitButton("NEXT");
        nextButton.setPrefWidth(WIDTH/6);
        nextButton.setLayoutX((WIDTH/2)-(WIDTH/12));
        nextButton.setLayoutY(SubBackground.getY());

        nextButton.setOnAction(e->{
            root.getChildren().remove(image1);
            root.getChildren().add(image2);
            root.getChildren().remove(nextButton);
            root.getChildren().remove(tutorial1);
            root.getChildren().add(tutorial2);
        });
        root.getChildren().add(nextButton);


    }
    public void subsceneImages() {
        try {
            image1 = new ImageView(new Image(new FileInputStream("src/main/resources/IMAGE1.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        image1.setFitHeight(HEIGHT/2);
        image1.setFitWidth(WIDTH/2);
        image1.setX(WIDTH/4);
        image1.setY(HEIGHT/3);
        try {
            image2 = new ImageView(new Image(new FileInputStream("src/main/resources/IMAGE2.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        image2.setFitHeight(HEIGHT/2);
        image2.setFitWidth(WIDTH/2);
        image2.setX(WIDTH/4);
        image2.setY(HEIGHT/3);

        root.getChildren().add(image1);
    }
    public void subsceneText() {
        tutorial1 = new PerfectFitLabel("Move the arrow keys to be the perfect fit for each obstacle");
        try {
            tutorial1.setFont(Font.loadFont("file:src/main/resources/heavy_data.ttf", 15));
        }catch(Exception e) {
            e.printStackTrace();
        }

        tutorial1.setLayoutX(exit.getLayoutX());
        tutorial1.setLayoutY(exit.getLayoutY()+50);
        tutorial2 = new PerfectFitLabel("Failing to avoid these obstacles results in YOUR DEATH!");
        try {
            tutorial2.setFont(Font.loadFont("file:src/main/resources/heavy_data.ttf", 15));
        }catch(Exception e) {
            e.printStackTrace();
        }
        tutorial2.setLayoutX(exit.getLayoutX());
        tutorial2.setLayoutY(exit.getLayoutY()+50);


        root.getChildren().add(tutorial1);
    }
    public void removeSubscene() {
        root.getChildren().remove(SubBackground);
        root.getChildren().remove(exit);
        root.getChildren().remove(nextButton);
        root.getChildren().remove(image1);
        root.getChildren().remove(tutorial1);
        root.getChildren().remove(image2);
        root.getChildren().remove(tutorial2);
        createHowToPlayButton();
        fadetwo.setToValue(1);
        fadethree.setToValue(1);
        fadetwo.play();
        fadethree.play();
    }


    public Scene getOpeningScene() {
        return openingScene;
    }




}
