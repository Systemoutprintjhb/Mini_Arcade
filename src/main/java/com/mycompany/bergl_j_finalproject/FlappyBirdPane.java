package com.mycompany.bergl_j_finalproject;
/*
Author: Jefrey Bergl
Instructor: Professor Bou-Saba
Class: CTIS 310
 */
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;

public class FlappyBirdPane {

    private Ellipse bird;
    private Rectangle ground;
    private Image cloud;
    private Image birdImg;
    private ImageView cloudView;
    private ImagePattern iP;
    private Label scorelabel;
    private Label l;
    private Label l2;
    private Button restartBtn;
    private int score;
    private ArrayList<Rectangle> columns = new ArrayList<Rectangle>();
    private int ticks;
    private int yMovement = 0;
    private Group root;
    private boolean gameOver = false;
    private LinearGradient lG;
    private IntegerStringConverter converter = new IntegerStringConverter();
    private Timeline tLine;
    private int xCoord, yCoord;
    private int W = 800, H = 700;
    private Scene scene;
    private Stage stage;

    public FlappyBirdPane() {
        stage = new Stage();
        root = new Group();

        birdImg = new Image("file:birdFrame0.png");
        iP = new ImagePattern(birdImg);//link to image pattern class overview: https://www.geeksforgeeks.org/javafx-imagepattern-class/

        cloud = new Image("file:cloud.png");//create clound and set X and Y coords
        cloudView = new ImageView(cloud);
        xCoord = W + (int) cloud.getWidth();
        cloudView.setX(xCoord);
        yCoord = (int) (Math.random() * 100) + 10;
        cloudView.setY(yCoord);

        //Bird shadow
        DropShadow ds1 = new DropShadow(); //link to drop shadow class overview: https://www.geeksforgeeks.org/javafx-dropshadow-class/
        ds1.setOffsetY(3.0f);
        ds1.setOffsetX(3.0f);
        ds1.setColor(Color.BLACK);

        //Button and Column shadow
        DropShadow ds2 = new DropShadow();
        ds1.setOffsetY(3.0f);
        ds1.setOffsetX(3.0f);
        ds1.setColor(Color.BLACK);

        bird = new Ellipse(W / 2 - 10, H / 2 - 10, (birdImg.getWidth() / 2) + 2, (birdImg.getHeight() / 2) + 2);//ellipse represents the bird shape/size
        bird.setFill(iP);
        bird.setEffect(ds1);

        lG = new LinearGradient(0.0, 0.0, 1.0, 0.0, true, CycleMethod.NO_CYCLE, new Stop(0.0, Color.GREY), new Stop(1.0, Color.BLACK));//link to linear gradient class overview: https://www.geeksforgeeks.org/javafx-lineargradient-class/

        l = new Label();
        l2 = new Label();
        scorelabel = new Label();
        scorelabel.setFont(new Font("Arial", 25));

        ground = new Rectangle(0, H - 120, W, 120);
        ground.setFill(Color.GREEN);

        tLine = new Timeline();//timeline class documentation: https://docs.oracle.com/javase/8/javafx/api/javafx/animation/Timeline.html
        tLine.setCycleCount(Animation.INDEFINITE);

        restartBtn = new Button();
        restartBtn.setText("Restart");
        restartBtn.setTranslateX(350);
        restartBtn.setTranslateY(600);
        restartBtn.setPrefSize(100, 50);
        restartBtn.setTextFill(Color.BLUE);
        restartBtn.setFont(new Font("Arial", 20));
        restartBtn.setEffect(ds2);

        KeyFrame kf = new KeyFrame(Duration.millis(20), e -> {//key frame doc: https://docs.oracle.com/javase/8/javafx/api/javafx/animation/KeyFrame.html

            ticks++;
            if (ticks % 2 == 0 && yMovement < 20) {//increases yCoord drop for the bird every other tick, until 20
                yMovement += 2;

            }
            xCoord -= 4;
            cloudView.setX(xCoord);

            if (xCoord < (0 - (int) cloud.getWidth())) {//create a new cloud when necessary
                xCoord = W + (int) cloud.getWidth();
                cloudView.setX(xCoord);
                yCoord = 10 + (int) (Math.random() * 100);
                cloudView.setY(yCoord);
            }
            int y = (int) bird.getCenterY() + yMovement;
            bird.setCenterY(y);
            scene.setOnKeyReleased(k -> {
                String code = k.getCode().toString();
                if (code.equals("UP")) {//if the up key is released, the bird jumps and the sound is played
                    Jump();
                }
            });
            Collision();
            if (gameOver) {
                if (!(root.getChildren().contains(l))) {//add final score and restart button
                    root.getChildren().addAll(l, restartBtn);
                    AudioPlayer.playFlappyBirdFailAudio();
                }
                
                restartBtn.setOnMouseClicked(x -> {//if restart button is clicked, restart the game
                    root.getChildren().remove(l);
                    restart();

                });

            }

        });

        KeyFrame kf2 = new KeyFrame(Duration.millis(20), e -> {//key frame doc: https://docs.oracle.com/javase/8/javafx/api/javafx/animation/KeyFrame.html

            for (int i = 0; i < columns.size(); i++) {//set color, shadow, and xCoord for columns

                Rectangle column = columns.get(i);
                column.setFill(lG);
                column.setEffect(ds1);
                column.setX((column.getX() - 5));

                if (column.getY() == 0 && bird.getCenterX() + bird.getRadiusX() > column.getX() + column.getWidth() / 2 - 5 && bird.getCenterX() + bird.getRadiusX() < column.getX() + column.getWidth() / 2 + 5) {//if bird passes through the column, add to score
                    score++;
                    scorelabel.setText("Score:" + converter.toString(score / 2));
                }

            }

            for (int i = 0; i < columns.size(); i++) {

                Rectangle currColumn = columns.get(i);

                if ((currColumn.getX() + currColumn.getWidth()) < 0) {//if column is out of the frame, remove it from the ArrayList
                    columns.remove(i);
                }
            }

        });

        tLine.getKeyFrames().addAll(kf, kf2);

        root.getChildren().add(ground);
        root.getChildren().add(bird);
        root.getChildren().add(cloudView);
        root.getChildren().addAll(scorelabel);

        scene = new Scene(root);
        scene.setFill(Color.rgb(135, 206, 235));//sky blue rgb
        restart();

        stage.setScene(scene);
        stage.setHeight(H);
        stage.setWidth(W);
        stage.setTitle("Flappy Bird");
        stage.setResizable(false);
        stage.show();
    }

    void addColumn() {//add columns with 300 pixels between them, a width of 100, and random height
        int between = 300;
        int width = 100;
        int height = (int) (Math.random() * 300) + 55;

        columns.add(new Rectangle(W + width + (columns.size() * 200), H - height - 120, width, height));//top column
        columns.add(new Rectangle(W + width + (columns.size() - 1) * 200, 0, width, H - height - between));//bottom column

    }

    void Collision() {//if the bird collids with the columns or the ground gameOver is true
        if (bird.getCenterY() > H - 120 || bird.getCenterY() < 0) {//ground/sky collision
            gameOver = true;
        }
        for (Rectangle column : columns) {//column collision
            if ((column.getBoundsInParent().intersects(bird.getBoundsInParent()))) {
                gameOver = true;
                if (bird.getCenterX() <= column.getX()) {
                    bird.setCenterX(bird.getRadiusX() + 10 * column.getX() - 2);
                } else {
                    if (column.getY() != 0) {
                        bird.setCenterY(column.getY() - 2 * bird.getRadiusY());
                    } else if (bird.getCenterY() > column.getHeight()) {
                        bird.setCenterY(column.getHeight());
                    }
                }
            }
        }

        if (gameOver) {//if the player loses, make the bird invisible and show final score
            bird.setCenterY(H - 120 - bird.getRadiusY());
            l.setText("You Lost!\nFinal Score: " + converter.toString(score / 2));
            l.setFont(new Font("Arial", 75));
            l.setLayoutX(stage.getWidth() / 2 - 225);
            l.setLayoutY(stage.getHeight() / 2 - 100);
            l.setTextFill(Color.RED);

        }
    }

    void Jump() {//bird jump action
        if (!gameOver) {//if the game is still running, reset yMovement, then reset it to 0, then subtract 10
            if (yMovement > 0) {
                yMovement = 0;
            }
            AudioPlayer.playWingFlapAudio();
            yMovement = yMovement - 10;
        }

    }

    void restart() {//resets all necessary components to replay, including the timeline
        bird.setCenterX(W / 2 - 10);
        bird.setCenterY(H / 2 - 10);
        gameOver = false;
        yMovement = 0;
        score = 0;
        scorelabel.setText("Score: " + converter.toString(score));
        root.getChildren().remove(restartBtn);
        root.getChildren().removeAll(columns);
        columns.clear();
        int i = 0;
        while (i < 20) {
            addColumn();
            i++;
        }
        l2.setText("Press UP arrow to start!");//remind player of controls
        l2.setFont(new Font("Arial", 50));
        l2.setLayoutX(stage.getWidth() / 2 - 250);
        l2.setLayoutY(stage.getHeight() / 2 - 100);
        l2.setTextFill(Color.DARKGREEN);

        tLine.pause();
        root.getChildren().add(l2);

        scene.setOnKeyReleased(k -> {//event handler for up arrow
            String code = k.getCode().toString();
            if (code.equals("UP")) {
                root.getChildren().addAll(columns);
                root.getChildren().remove(l2);
                tLine.play();
            }
        });
    }
}
