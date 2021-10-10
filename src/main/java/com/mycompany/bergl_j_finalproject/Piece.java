package com.mycompany.bergl_j_finalproject;
/*
Author: Jefrey Bergl
Instructor: Professor Bou-Saba
Class: CTIS 310
*/
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import static com.mycompany.bergl_j_finalproject.CheckersPane.SizeOfTile;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

enum KindOfPieceMovement {//used to signify the types of moves, I learned about enumerations from here: https://www.geeksforgeeks.org/enum-in-java/
    NONE, REGULAR, TAKE;
}

public class Piece extends StackPane {

    public PieceColor color;
    private double priorPieceXCoordinate, priorPieceYCoordinate;
    private double clickPositionX, clickPositionY;
    private Circle circle;
    public FadeTransition ft;

    public PieceColor getColor() {
        return color;
    }

    public PieceColor setColor(PieceColor color) {
        this.color = color;
        return color;
    }

    public Circle getCircle() {
        return circle;
    }

    public double getPriorPieceXCoordinate() {
        return priorPieceXCoordinate;
    }

    public double getPriorPieceYCoordinate() {
        return priorPieceYCoordinate;
    }

    public Piece(PieceColor color, double pieceXCoordinate, double pieceYCoordinate) {
        this.color = color;

        double PieceRadius = SizeOfTile * .45;
        circle = new Circle(PieceRadius);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(SizeOfTile * .04);

        if (color == PieceColor.RED) {
            circle.setFill(Color.RED);
        } else if (color == PieceColor.GREEN) {
            circle.setFill(Color.GREEN);
        }

        setMovedPieceCoordinates(pieceXCoordinate, pieceYCoordinate);
        getChildren().add(circle);

        setOnMousePressed(this::getMousePressPosition);
        setOnMouseDragged(this::movePiece);
    }

    public void setMovedPieceCoordinates(double pieceXCoordinate, double pieceYCoodrinate) {
        priorPieceXCoordinate = pieceXCoordinate * SizeOfTile;
        priorPieceYCoordinate = pieceYCoodrinate * SizeOfTile;
        relocate(priorPieceXCoordinate, priorPieceYCoordinate);//https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Node.html#relocate-double-double-
    }

    public void getMousePressPosition(MouseEvent event) {
        clickPositionX = event.getSceneX();
        clickPositionY = event.getSceneY();
    }

    public void movePiece(MouseEvent event) {
        relocate(event.getSceneX() - clickPositionX + priorPieceXCoordinate, event.getSceneY() - clickPositionY + priorPieceYCoordinate);//https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Node.html#relocate-double-double-
    }

    public void illegalMove() {
        relocate(priorPieceXCoordinate, priorPieceYCoordinate);//https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Node.html#relocate-double-double-

    }

    public void setKingColor(Piece piece) {//first king on the "kinged" team flashes
        if (color == PieceColor.GREENKING || color == PieceColor.REDKING) {
            ft = new FadeTransition(Duration.millis(250), piece);
            ft.setFromValue(1);
            ft.setToValue(.5);
            ft.setCycleCount(Timeline.INDEFINITE);
            ft.setAutoReverse(true);
            ft.play();
        }
    }
}
