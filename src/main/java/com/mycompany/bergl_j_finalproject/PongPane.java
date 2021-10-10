package com.mycompany.bergl_j_finalproject;
/*
Author: Jefrey Bergl
Instructor: Professor Bou-Saba
Class: CTIS 310
*/
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PongPane {
    private static final int width = 800;
    private static final int height = 600;
    private static final int playerWidth = 20;
    private static final int playerHeight = 100;
    private static final int bRadius = 20;
    private static int bXSpeed = 1;
    private static int bYSpeed = 1;
    private int p1Score = 0;
    private int p2Score = 0;
    private int p1XPos = 0;
    private double p1YPos = height / 2;
    private double p2XPos = width - playerWidth;
    private double p2YPos = height / 2;
    private double bXPos = width / 2;
    private double bYPos = height / 2;
    private boolean running;
    private Stage stage;

    public PongPane() {
        stage = new Stage();
        Canvas c = new Canvas(width, height);
        GraphicsContext gContext = c.getGraphicsContext2D();//graphics context doc: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/canvas/GraphicsContext.html
        Timeline tLine = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gContext)));//timeline doc: https://docs.oracle.com/javase/8/javafx/api/javafx/animation/Timeline.html
        tLine.setCycleCount(Timeline.INDEFINITE);

        //event handlers
        c.setOnMouseClicked(e -> running = true);
        c.setOnMouseMoved(e -> p1YPos = e.getY());

        stage.setResizable(false);
        stage.setScene(new Scene(new StackPane(c)));
        stage.setTitle("Pong Game!");
        stage.show();
        tLine.play();
    }

    private void run(GraphicsContext gContext) {
        //background color: black
        gContext.setFill(Color.BLACK);
        gContext.fillRect(0, 0, width, height);

        //text color
        gContext.setFill(Color.GREEN);
        gContext.setFont(Font.font(30));

        //set ball movement
        if (running) { //game running
            bXPos += bXSpeed;
            bYPos += bYSpeed;

            //computer logic (follow the ball)
            if (bXPos < width - width / 4) {
                p2YPos = bYPos - playerHeight / 2;
            } else {
                p2YPos = ((bYPos > p2YPos + playerHeight / 2) ? p2YPos += 1 : p2YPos - 1);
            }

            gContext.fillOval(bXPos, bYPos, bRadius, bRadius);
        } else {//game stopped
            gContext.setStroke(Color.GREEN);
            gContext.setTextAlign(TextAlignment.CENTER);
            gContext.strokeText("PONG\nClick in Window to Start", width / 2, height / 2);

            //reset ball position to starting location
            bXPos = width / 2;
            bYPos = height / 2;

            //reset ball direction and speed
            bXSpeed = ((new Random().nextInt(2) == 0) ? 1 : -1);
            bYSpeed = ((new Random().nextInt(2) == 0) ? 1 : -1);
        }

        //ensure ball stays in frame
        if (bYPos < 0 || bYPos > height) {
            bYSpeed *= -1;
        }
        
        //ensure players stay in frame
        if (p1YPos + playerHeight > height) {
            p1YPos = height - playerHeight;
        }
        if (p2YPos + playerHeight > height) {
            p2YPos = height - playerHeight;
        }
        //computer scores
        if (bXPos < p1XPos - playerWidth) {
            p2Score++;
            running = false;
            AudioPlayer.playPongLoseAudio();
        }

        //player scores
        if (bXPos > p2XPos + playerWidth) {
            p1Score++;
            running = false;
            AudioPlayer.playPongWinAudio();
        }
        
        //make ball travel faster as game goes on
        if ((bXPos + bRadius > p2XPos) && (bYPos >= p2YPos) && (bYPos <= p2YPos + playerHeight)
                || (bXPos < p1XPos + playerWidth) && (bYPos >= p1YPos) && (bYPos <= p1YPos + playerHeight)) {
            bXSpeed += 1 * Math.signum(bXSpeed); //info about Math.signum https://www.geeksforgeeks.org/java-signum-method-examples/
            bYSpeed += 1 * Math.signum(bYSpeed);
            bXSpeed *= -1;
            bYSpeed *= -1;
            AudioPlayer.playPongBounceAudio();
        }

        //include score text
        gContext.fillText("Player: " + p1Score + "\t\t\t\t\t\t" + "Computer: " + p2Score, width / 2, 50);

        //include player rectangles
        gContext.fillRect(p1XPos, p1YPos, playerWidth, playerHeight);
        gContext.fillRect(p2XPos, p2YPos, playerWidth, playerHeight);
    }
}
