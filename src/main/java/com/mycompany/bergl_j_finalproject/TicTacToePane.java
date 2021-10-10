package com.mycompany.bergl_j_finalproject;
/*
Author: Jefrey Bergl
Instructor: Professor Bou-Saba
Class: CTIS 310
*/
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class TicTacToePane {

    private char currentPlayer = 'O';//O plays first
    private Cell[][] TicTaccell = new Cell[3][3];
    private Label gameDisplay = new Label("PLAYER 'O' GOES FIRST!");
    public Scene TicTacscene;
    private Stage TicTacStage;
    private BorderPane gameDisplayPane;

    public TicTacToePane() {
        GridPane theSquarePane = new GridPane();
        TicTacStage = new Stage();

        for (int xCoord = 0; xCoord < 3; xCoord++) {//creates all of the 9 TicTacToe tiles
            for (int yCoord = 0; yCoord < 3; yCoord++) {
                TicTaccell[xCoord][yCoord] = new Cell();

                theSquarePane.add(TicTaccell[xCoord][yCoord], yCoord, xCoord);
            }
        }

        gameDisplayPane = new BorderPane();
        gameDisplayPane.setTop(gameDisplay);
        gameDisplayPane.setCenter(theSquarePane);

        TicTacscene = new Scene(gameDisplayPane, 500, 500);

        TicTacStage.setResizable(false);
        TicTacStage.setTitle("Tic Tac Toe!");
        TicTacStage.setScene(TicTacscene);
        TicTacStage.show();
    }

    public class Cell extends StackPane {//handles everything that goes on inside each of the TicTacToe tiles

        private char theOtherPlayer = ' ';
        Group xGroup = new Group();
        Group oGroup = new Group();

        public Cell() {
            this.setPrefSize(500, 500);
            setStyle("-fx-border-color:black");

            setOnMouseClicked(this::processMouseClick);
        }

        public char getTicTacPlayer() {
            return theOtherPlayer;
        }

        public void setTicTacPlayer(char p) {//sets the objects to each player's move, p stands for player
            theOtherPlayer = p;

            if (theOtherPlayer == 'O') {
                double Oradius = TicTacscene.getWidth() / 3 - TicTacscene.getWidth() / 5;
                double Ox = TicTacscene.getWidth() / 3 - TicTacscene.getWidth() / 6;
                double Oy = TicTacscene.getHeight() / 3 - TicTacscene.getHeight() / 6;
                Circle circle = new Circle(Ox, Oy, Oradius);
                circle.setStrokeWidth(5);
                circle.setStroke(Color.GREEN);
                circle.setFill(null);

                oGroup.getChildren().add(circle);
                getChildren().add(oGroup);

            }
            if (theOtherPlayer == 'X') {
                Line firstXLine = new Line(TicTacscene.getWidth() / 20, TicTacscene.getHeight() / 20, TicTacscene.getWidth() / 3.25, TicTacscene.getHeight() / 3.25);
                firstXLine.setStrokeWidth(5);
                firstXLine.setStroke(Color.RED);
                Line secondXLine = new Line(TicTacscene.getWidth() / 20, TicTacscene.getHeight() / 3.25, TicTacscene.getWidth() / 3.25, TicTacscene.getHeight() / 20);
                secondXLine.setStrokeWidth(5);
                secondXLine.setStroke(Color.RED);

                xGroup.getChildren().addAll(firstXLine, secondXLine);
                getChildren().add(xGroup);

            }
        }

        private void processMouseClick(MouseEvent e) {
            if (aPlayerWon('X') != true && aPlayerWon('O') != true) {
                if (currentPlayer == 'X' && theOtherPlayer == ' ' || currentPlayer == 'O' && theOtherPlayer == ' ') {//makes sure the current player is, in fact, X or O
                    setTicTacPlayer(currentPlayer);
                    if (aPlayerWon(currentPlayer)) {
                        gameDisplay.setText("PLAYER " + currentPlayer + " WON!");
                        AudioPlayer.playTicTacToeWinAudio();
                    } else if (isTheBoardFull()) {
                        gameDisplay.setText("THE GAME WAS A DRAW!");
                        currentPlayer = ' ';
                        AudioPlayer.playTicTacToeTieAudio();
                    } else {
                        currentPlayer = (currentPlayer == 'O') ? 'X' : 'O';//learned the question mark operator from this website: http://www.beginwithjava.com/java/decisions/conditional-operator.html
                        gameDisplay.setText("PLAYER " + currentPlayer + "'s TURN!");
                        AudioPlayer.playPieceMoveAudio();
                    }
                }
            }
        }
    }

    public boolean isTheBoardFull() {
        for (int xCoord = 0; xCoord < 3; xCoord++) {
            for (int yCoord = 0; yCoord < 3; yCoord++) {
                if (TicTaccell[xCoord][yCoord].getTicTacPlayer() == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean aPlayerWon(char player) {
        for (int i = 0; i < 3; i++) {//loop to see if a player won horizontally
            if (TicTaccell[0][i].getTicTacPlayer() == player && TicTaccell[1][i].getTicTacPlayer() == player && TicTaccell[2][i].getTicTacPlayer() == player) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {//loop to see if a player won vertically
            if (TicTaccell[i][0].getTicTacPlayer() == player && TicTaccell[i][1].getTicTacPlayer() == player && TicTaccell[i][2].getTicTacPlayer() == player) {
                return true;
            }
        }

        //conditionals are for if a player wins diagonally
        if (TicTaccell[0][0].getTicTacPlayer() == player && TicTaccell[1][1].getTicTacPlayer() == player && TicTaccell[2][2].getTicTacPlayer() == player) {
            return true;
        }

        if (TicTaccell[2][0].getTicTacPlayer() == player && TicTaccell[1][1].getTicTacPlayer() == player && TicTaccell[0][2].getTicTacPlayer() == player) {
            return true;
        }
        return false;
    }
}
