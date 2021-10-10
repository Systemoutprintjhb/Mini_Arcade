package com.mycompany.bergl_j_finalproject;

/*
Author: Jefrey Bergl
Instructor: Professor Bou-Saba
Class: CTIS 310
 */
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainMenu {

    private Label title;
    private Label singlePlayerLabel;
    private Label twoPlayerLabel;
    private Button flappyButton;
    private Button checkersButton;
    private Button ticTacToeButton;
    private Button pongButton;
    private Image flappyImage = new Image("file:Flappy.jpg");
    private Image checkersImage = new Image("file:Checkers.jpg");
    private Image ticTacToeImage = new Image("file:TicTacToe.jpg");
    private Image pongImage = new Image("file:pong.jpg");
    private Image background = new Image("file:Neverending.gif");
    private ImageView flappyView = new ImageView(flappyImage);
    private ImageView checkersView = new ImageView(checkersImage);
    private ImageView ticTacToeView = new ImageView(ticTacToeImage);
    private ImageView pongView = new ImageView(pongImage);
    private ImageView backgroundView = new ImageView(background);

    public MainMenu(Stage menuStage) {//set and organize buttons, images, and titles
        Pane mainPane = new Pane();
        Scene mainScene = mainScene = new Scene(mainPane, 650, 700);

        title = new Label("MINI ARCADE");
        title.setFont(Font.font(80));
        title.setTranslateX(70);
        title.setTextFill(Color.WHITE);

        singlePlayerLabel = new Label("Single Player");
        singlePlayerLabel.setFont(Font.font(40));
        singlePlayerLabel.setTranslateX(30);
        singlePlayerLabel.setTranslateY(130);
        singlePlayerLabel.setTextFill(Color.WHITE);
        twoPlayerLabel = new Label("Two Players");
        twoPlayerLabel.setFont(Font.font(40));
        twoPlayerLabel.setTranslateX(mainScene.getWidth() / 2 + 60);
        twoPlayerLabel.setTranslateY(130);
        twoPlayerLabel.setTextFill(Color.WHITE);

        flappyView.setPreserveRatio(true);
        pongView.setPreserveRatio(true);
        ticTacToeView.setPreserveRatio(true);
        checkersView.setPreserveRatio(true);
        backgroundView.setPreserveRatio(false);
        flappyView.setX(singlePlayerLabel.getTranslateX());
        pongView.setX(singlePlayerLabel.getTranslateX());
        flappyView.setY(singlePlayerLabel.getTranslateY() + 100);
        pongView.setY(flappyView.getY() + 200);
        flappyView.setFitHeight(125);
        pongView.setFitHeight(125);
        checkersView.setX(twoPlayerLabel.getTranslateX());
        ticTacToeView.setX(twoPlayerLabel.getTranslateX());
        checkersView.setY(twoPlayerLabel.getTranslateY() + 100);
        ticTacToeView.setY(checkersView.getY() + 200);
        checkersView.setFitHeight(125);
        ticTacToeView.setFitHeight(125);
        backgroundView.setX(0);
        backgroundView.setY(0);
        backgroundView.setFitWidth(mainScene.getWidth());
        backgroundView.setFitHeight(mainScene.getHeight());

        flappyButton = new Button("Play Flappy Bird");
        pongButton = new Button("Play Pong");
        checkersButton = new Button("Play Checkers");
        ticTacToeButton = new Button("Play Tic Tac Toe");
        flappyButton.setTranslateX(singlePlayerLabel.getTranslateX());
        flappyButton.setTranslateY(flappyView.getY() + 126);
        pongButton.setTranslateX(singlePlayerLabel.getTranslateX());
        pongButton.setTranslateY(pongView.getY() + 126);
        checkersButton.setTranslateX(twoPlayerLabel.getTranslateX());
        checkersButton.setTranslateY(checkersView.getY() + 126);
        ticTacToeButton.setTranslateX(twoPlayerLabel.getTranslateX());
        ticTacToeButton.setTranslateY(ticTacToeView.getY() + 126);

        mainPane.getChildren().add(backgroundView);
        mainPane.getChildren().add(title);
        mainPane.getChildren().add(singlePlayerLabel);
        mainPane.getChildren().add(twoPlayerLabel);
        mainPane.getChildren().add(flappyView);
        mainPane.getChildren().add(pongView);
        mainPane.getChildren().add(checkersView);
        mainPane.getChildren().add(ticTacToeView);
        mainPane.getChildren().add(flappyButton);
        mainPane.getChildren().add(pongButton);
        mainPane.getChildren().add(checkersButton);
        mainPane.getChildren().add(ticTacToeButton);

        ticTacToeButton.setOnAction(this::processButton);
        checkersButton.setOnAction(this::processButton);
        flappyButton.setOnAction(this::processButton);
        pongButton.setOnAction(this::processButton);

        menuStage.setResizable(false);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(mainScene);
        menuStage.show();
    }

    public void processButton(ActionEvent e) {
        if (e.getSource() == ticTacToeButton) {
            TicTacToePane tPane = new TicTacToePane();
        } else if (e.getSource() == checkersButton) {
            CheckersPane cPane = new CheckersPane();
        } else if (e.getSource() == flappyButton) {
            FlappyBirdPane fPane = new FlappyBirdPane();
        } else if (e.getSource() == pongButton) {
            PongPane pPane = new PongPane();
        }
    }

}
