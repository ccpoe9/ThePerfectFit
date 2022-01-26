package com.theperfectfit.theperfectfit;

import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.Random;

public class GameScene {
    Stage stage;
    Scene gameScene;
    AnchorPane root;
    ImageView Background;
    ImageView player;
    ImageView obstacleleft;
    ImageView obstacleright;
    PerfectFitLabel score;
    PerfectFitLabel scoreTitle;
    PerfectFitButton pause;
    Timeline timeline;
    Random random;
    TranslateTransition linetrans1;
    TranslateTransition linetrans2;
    TranslateTransition move;
    int obsspeed=2000;
    int scorenum=0;
    int rand;
    int velX;
    boolean init=true;
    double WIDTH;
    double HEIGHT;
    double PLAYERHEIGHT;
    double PLAYERWIDTH;
    double PLAYERPOSX;
    double PLAYERPOSY;
    double SCOREPOSX;
    double SCORETITLEPOSX;
    double OBSWIDTH;
    double OBSHEIGHT;
    EventHandler<KeyEvent> keyClicked;
    ChangeListener<Bounds> leftListener;
    ChangeListener<Bounds> rightListener;
    public void createGameScene(double w, double h,Stage stage) {
        this.stage = stage;
        WIDTH=w;
        HEIGHT=h;
        PLAYERHEIGHT=HEIGHT/10;
        PLAYERWIDTH=(WIDTH/10)-2;
        velX=(int) (WIDTH/10);
        PLAYERPOSX=(WIDTH/2)+1;
        PLAYERPOSY=HEIGHT-PLAYERHEIGHT;
        OBSHEIGHT = PLAYERHEIGHT/2;
        createBackground();
        createPlayer();
        createObstacles();
        createScore();
        gameScene = new Scene(root,w,h);
        createPlayerController(velX);
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
    public void createPlayer() {

        try {
            player = new ImageView(new Image(new FileInputStream("src/main/resources/Player1.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        player.setFitHeight(PLAYERHEIGHT);
        player.setFitWidth(PLAYERWIDTH);
        player.setX(PLAYERPOSX);
        player.setY(PLAYERPOSY);
        root.getChildren().add(player);


    }
    public void createObstacles() {
        try {
            obstacleleft = new ImageView(new Image(new FileInputStream("src/main/resources/obstacle.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        random = new Random();
        rand = (random.nextInt(10));

        obstacleleft.setY(-OBSHEIGHT);
        OBSWIDTH =(WIDTH/10)*rand;
        if(OBSWIDTH==0) {
            obstacleleft.setX(OBSWIDTH+(WIDTH/10));
        }
        else {
            obstacleleft.setX(0);
        }
        obstacleleft.setFitHeight(OBSHEIGHT);
        obstacleleft.setFitWidth(OBSWIDTH);
        try {
            obstacleright = new ImageView(new Image(new FileInputStream("src/main/resources/obstacle.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        obstacleright.setY(-OBSHEIGHT);
        obstacleright.setX(obstacleleft.getFitWidth()+WIDTH/10);
        obstacleright.setFitHeight(OBSHEIGHT);
        obstacleright.setFitWidth(WIDTH - (OBSWIDTH+WIDTH/10));

        leftListener = new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> arg0, Bounds arg1, Bounds arg2) {
                if(obstacleleft.getBoundsInParent().intersects(player.getBoundsInParent())) {
                    obstacleleft.boundsInParentProperty().removeListener(leftListener);
                    gameOver();
                }

            }
        };
        rightListener = new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> arg0, Bounds arg1, Bounds arg2) {
                if(obstacleright.getBoundsInParent().intersects(player.getBoundsInParent())) {
                    obstacleright.boundsInParentProperty().removeListener(rightListener);
                    gameOver();
                }

            }
        };
        obstacleleft.boundsInParentProperty().addListener(leftListener);
        obstacleright.boundsInParentProperty().addListener(rightListener);

        moveObstacle();
        root.getChildren().addAll(obstacleleft,obstacleright);


    }
    public void moveObstacle() {


        linetrans1 = new TranslateTransition();
        linetrans1.setCycleCount(1);
        linetrans1.setDuration(Duration.millis(obsspeed));
        linetrans1.setNode(obstacleleft);

        linetrans2 = new TranslateTransition();
        linetrans2.setCycleCount(1);
        linetrans2.setDuration(Duration.millis(obsspeed));
        linetrans2.setNode(obstacleright);

        linetrans1.setByY(HEIGHT+PLAYERHEIGHT);
        linetrans2.setByY(HEIGHT+PLAYERHEIGHT);

        if(init) {
            linetrans1.setDelay(Duration.millis(2000));
            linetrans2.setDelay(Duration.millis(2000));
            init=false;
        }
        linetrans1.play();
        linetrans2.play();


        linetrans2.setOnFinished(e->{
            root.getChildren().remove(obstacleleft);
            root.getChildren().remove(obstacleright);
            createObstacles();
        });

    }
    public void createScore() {

        score = new PerfectFitLabel(String.valueOf(scorenum));
        scoreTitle = new PerfectFitLabel("SCORE");

        SCORETITLEPOSX = WIDTH/2 - (WIDTH/16);
        SCOREPOSX =WIDTH/2 - (WIDTH/50);

        scoreTitle.setLayoutX(SCORETITLEPOSX);

        score.setLayoutX(SCOREPOSX);

        score.setLayoutY(HEIGHT/20);

        root.getChildren().add(scoreTitle);
        root.getChildren().add(score);
        timeline =
                new Timeline(new KeyFrame(Duration.millis(1000), e ->{
                    scorenum++;
                    score.setText(String.valueOf(scorenum));
                    obsspeed-=10;
                    //createPlayerController(velX);
                }));
        timeline.setCycleCount(Animation.INDEFINITE); // loop forever
        timeline.setDelay(Duration.millis(2000));
        timeline.play();



    }

    private void createPlayerController(int velX) {
        keyClicked = (e->{
            switch(e.getCode()) {
                case LEFT:
                    if(player.getX()-velX>1 ) {
                        player.setX(player.getX()-velX);
                    }
                    else{
                        player.setX(1);
                    }
                    break;
                case RIGHT:
                    if(player.getX()+velX<(WIDTH-player.getFitWidth()-1)) {
                        player.setX(player.getX()+velX);
                    }
                    else {
                        player.setX(WIDTH-player.getFitWidth()-1);
                    }
                    break;
                default:
                    break;
            }
        });
        gameScene.setOnKeyPressed(keyClicked);



    }
    public void gameOver() {
        linetrans1.pause();
        linetrans2.pause();
        timeline.pause();
        gameScene.addEventFilter(KeyEvent.ANY, Event::consume);
        playerDied();
        checkScores();
        fadeOut(root,1);


    }
    public void playerDied() {
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(-1.0);

        Blend blush = new Blend(
                BlendMode.MULTIPLY,
                monochrome,
                new ColorInput(
                        player.getX(),
                        player.getY(),
                        player.getFitWidth(),
                        player.getFitHeight(),
                        Color.RED
                )
        );
        player.setEffect(blush);
        TranslateTransition fall = new TranslateTransition();

        fall.setDuration(Duration.millis(10000));
        fall.setNode(player);
        fall.setByY(HEIGHT+PLAYERHEIGHT);
        fall.play();
    }
    public void checkScores() {
        BufferedReader reader;
        FileWriter writer;
        String HighScore="";
        try {
            reader = new BufferedReader(new FileReader("src/main/resources/HighScore.txt"));
            HighScore = reader.readLine();
            if(scorenum>Integer.parseInt(HighScore)) {
                writer = new FileWriter("src/main/resources/HighScore.txt");
                writer.append(String.valueOf(scorenum)+"\n");
                writer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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
                switchToTryAgain();
            }


        });
    }
    public void clearScene() {
        root.getChildren().remove(score);
        root.getChildren().remove(scoreTitle);
        root.getChildren().remove(player);
        root.getChildren().remove(obstacleleft);
        root.getChildren().remove(obstacleright);
        root.getChildren().remove(Background);
    }
    public void switchToTryAgain() {
        //stage = (Stage) gameScene.getWindow();
        TryAgainScene tryAgainScene = new TryAgainScene();
        tryAgainScene.createTryAgainScene(WIDTH, HEIGHT, score, stage);
        Scene tryAgain = tryAgainScene.getScene();
        tryAgain.getRoot().setOpacity(0);
        stage.setScene(tryAgain);
        fadeIn((AnchorPane) tryAgain.getRoot());


    }

    public void fadeIn(AnchorPane root) {
        FadeTransition fadein = new FadeTransition();
        fadein.setDuration(Duration.millis(1000));
        fadein.setNode(root);
        fadein.setToValue(1);
        fadein.play();
    }

    public Scene getGameScene() {
        return gameScene;
    }






}
