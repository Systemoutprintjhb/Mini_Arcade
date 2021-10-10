package com.mycompany.bergl_j_finalproject;
/*
Author: Jefrey Bergl
Instructor: Professor Bou-Saba
Class: CTIS 310
*/
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle{
    
    private int sizeOfTile = CheckersPane.SizeOfTile;
    private Piece piece;
    
    public Tile(boolean lightOrDark, int xCoordinate, int yCoordinate) {
        setWidth(sizeOfTile);
        setHeight(sizeOfTile);
        
        relocate(xCoordinate*sizeOfTile, yCoordinate*sizeOfTile);//https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Node.html#relocate-double-double-
        
        setFill(lightOrDark ? Color.rgb(51, 0, 0) : Color.rgb(153, 102, 0));//learned the question mark operator from this website: http://www.beginwithjava.com/java/decisions/conditional-operator.html
    }
    
    public Piece getThePiece() {//get the piece in the tile
        return piece;
    }
    
    public void setThePiece(Piece piece) {//set the tile's piece
        this.piece = piece;
    }
    
    public boolean hasPiece() {//see if the tile has a piece currently
        return piece != null;
    }
}
