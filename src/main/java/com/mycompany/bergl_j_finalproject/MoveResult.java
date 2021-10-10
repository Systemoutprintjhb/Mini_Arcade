package com.mycompany.bergl_j_finalproject;
/*
Author: Jefrey Bergl
Instructor: Professor Bou-Saba
Class: CTIS 310
*/
public class MoveResult {

    private KindOfPieceMovement kind;
    private Piece Piece;

    public MoveResult(KindOfPieceMovement kind, Piece Piece) {//for if there is a piece to be referenced (like in instances of take)
        this.kind = kind;
        this.Piece = Piece;
    }

    public MoveResult(KindOfPieceMovement kind) {//for when there's no piece to be referenced (like moving into an open space without taking)
        this(kind, null);//kind of movement, then null for piece
    }

    public Piece getPiece() {
        return Piece;
    }

    public KindOfPieceMovement getPieceMovementKind() {
        return kind;
    }

}
