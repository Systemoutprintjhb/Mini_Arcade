package com.mycompany.bergl_j_finalproject;
/*
Author: Jefrey Bergl
Instructor: Professor Bou-Saba
Class: CTIS 310
*/
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CheckersPane {

    public static int SizeOfTile = 100;
    public int WidthOfTileRow = 8;
    public int HeightOfTileColumn = 8;
    private Tile[][] theBoard = new Tile[WidthOfTileRow][HeightOfTileColumn];//array to store tile info

    private Piece thePiece;
    private Group GroupForTiles = new Group();
    private Group GroupForPieces = new Group();
    private Stage CheckersStage = new Stage();
    

    public CheckersPane() {

        Pane CheckersRoot = new Pane();
        Scene scene = new Scene(CheckersRoot, WidthOfTileRow * SizeOfTile, HeightOfTileColumn * SizeOfTile);
        CheckersRoot.getChildren().addAll(GroupForTiles, GroupForPieces);

        for (int TilexCoordinate = 0; TilexCoordinate < WidthOfTileRow; TilexCoordinate++) {//creating and placing the tiles, starting at 0,0
            for (int TileyCoordinate = 0; TileyCoordinate < HeightOfTileColumn; TileyCoordinate++) {
                Tile theTile = new Tile((TilexCoordinate + TileyCoordinate) % 2 == 0, TilexCoordinate, TileyCoordinate);//decides whether the tile is light or dark (dark is when (xCoord+yCoord)/2 is a whole number)
                theBoard[TilexCoordinate][TileyCoordinate] = theTile;//making each tile part of the array

                GroupForTiles.getChildren().add(theTile);
                thePiece = null;

                if (TileyCoordinate < 3 && (TilexCoordinate + TileyCoordinate) % 2 != 0) {//creating and placing the pieces, but only placing them on the light squares
                    thePiece = createPiece(PieceColor.GREEN, TilexCoordinate, TileyCoordinate);
                }

                if (TileyCoordinate > 4 && (TilexCoordinate + TileyCoordinate) % 2 != 0) {
                    thePiece = createPiece(PieceColor.RED, TilexCoordinate, TileyCoordinate);
                }

                if (thePiece != null) {//in any place tile where the requirements were met, add a piece to the display and to the Group for pieces
                    theTile.setThePiece(thePiece);
                    GroupForPieces.getChildren().add(thePiece);
                }
            }
        }
        CheckersStage.setResizable(false);
        CheckersStage.setTitle("Checkers!");
        CheckersStage.setScene(scene);
        CheckersStage.show();
    }

    public int PixeltoBoardCoords(double pixel) {
        return (int) (pixel + SizeOfTile / 2) / SizeOfTile;//parses the pixel coordinates into integer coordinates of the board, used to center the moved pieces https://www.geeksforgeeks.org/convert-double-to-integer-in-java/
    }

    public MoveResult moveTest(int newX, int newY, Piece Piece) {//tests to see if the desired move is allowed, if not diagonal or already contains a piece, the piece is relocated back to its previous position
        if (theBoard[newX][newY].hasPiece() || (newX + newY) % 2 == 0) {
            return new MoveResult(KindOfPieceMovement.NONE);
        }
        
        if (Piece.getColor().MoveUpOrDown == -1 && newY == 0) {//if a piece makes it to the opposite side of the board, the team's pieces become kings
            Piece.getColor().MoveUpOrDown = 2;
            Piece.setColor(PieceColor.REDKING);
            Piece.setKingColor(Piece);
            AudioPlayer.playCheckersKingAudio();
        }
        if (Piece.getColor().MoveUpOrDown == 1 && newY == theBoard.length - 1) {
            Piece.getColor().MoveUpOrDown = 2;
            Piece.setColor(PieceColor.GREENKING);
            Piece.setKingColor(Piece);
            AudioPlayer.playCheckersKingAudio();
        }

        int PriorX = PixeltoBoardCoords(Piece.getPriorPieceXCoordinate());
        int PriorY = PixeltoBoardCoords(Piece.getPriorPieceYCoordinate());

        if (Piece.getColor().MoveUpOrDown == 1 || Piece.getColor().MoveUpOrDown == -1) {
            if (Math.abs(newX - PriorX) == 1 && newY - PriorY == Piece.getColor().MoveUpOrDown) {//if the move is only one square, and in the correct direction, it is allowed
                return new MoveResult(KindOfPieceMovement.REGULAR);
            } else if (Math.abs(newX - PriorX) == 2 && newY - PriorY == Piece.getColor().MoveUpOrDown * 2) {// if the distance between the new tile and the previous tile is 2, and the move is in the correct direction, it is allowed
                int TakeMovex = PriorX + (newX - PriorX) / 2;//TakeMovex and TakeMovey store the coords of the taken piece
                int TakeMovey = PriorY + (newY - PriorY) / 2;

                if (theBoard[TakeMovex][TakeMovey].hasPiece() && theBoard[TakeMovex][TakeMovey].getThePiece().getColor() != Piece.getColor()) {//if the skipped tile has a piece of the opposite color, it is allowed
                    return new MoveResult(KindOfPieceMovement.TAKE, theBoard[TakeMovex][TakeMovey].getThePiece());
                }
            }
        } else if (Piece.getColor().MoveUpOrDown == 2) {//move rules for once a king is created
            if (Math.abs(newX - PriorX) == 1) {
                return new MoveResult(KindOfPieceMovement.REGULAR);
            } else if (Math.abs(newX - PriorX) == 2) {
                int TakeMovex = PriorX + (newX - PriorX) / 2;
                int TakeMovey = PriorY + (newY - PriorY) / 2;

                if (theBoard[TakeMovex][TakeMovey].hasPiece() && theBoard[TakeMovex][TakeMovey].getThePiece().getColor() != Piece.getColor()) {//if the skipped tile has a piece of the opposite color, it is allowed
                    return new MoveResult(KindOfPieceMovement.TAKE, theBoard[TakeMovex][TakeMovey].getThePiece());
                }
            }
        }
        return new MoveResult(KindOfPieceMovement.NONE);//if the piece isn't moved, the coords don't change
    }

    public Piece createPiece(PieceColor color, double pieceXCoordinate, double pieceYCoordinate) {
        Piece Piece = new Piece(color, pieceXCoordinate, pieceYCoordinate);

        Piece.setOnMouseReleased(piecePlaced -> {//arrow seperates parameters from contents http://math.hws.edu/javanotes/c6/s3.html (section 6.32)
            int newX = PixeltoBoardCoords(Piece.getLayoutX());//gets new board coords
            int newY = PixeltoBoardCoords(Piece.getLayoutY());

            MoveResult result = moveTest(newX, newY, Piece);//sees if the move is allowed, then gives the result

            int Priorx = PixeltoBoardCoords(Piece.getPriorPieceXCoordinate());
            int Priory = PixeltoBoardCoords(Piece.getPriorPieceYCoordinate());

            switch (result.getPieceMovementKind()) {//decides which code to perform based on the kind of movement https://beginnersbook.com/2017/08/java-switch-case/#:~:text=Switch%20case%20statement%20is%20used,default%3A%20%2F%2FJava%20code%20%3B%20%7D
                case REGULAR:
                    Piece.setMovedPieceCoordinates(newX, newY);//sets the new piece coords
                    theBoard[Priorx][Priory].setThePiece(null);//removes the piece from theBoard array
                    theBoard[newX][newY].setThePiece(Piece);//moves the piece to the new board coords of the array
                    AudioPlayer.playCheckersMoveAudio();
                    break;
                case TAKE:
                    Piece.setMovedPieceCoordinates(newX, newY);//same as regular move
                    theBoard[Priorx][Priory].setThePiece(null);
                    theBoard[newX][newY].setThePiece(Piece);
                    Piece theOtherPiece = result.getPiece();//see if the take is allowed
                    theBoard[PixeltoBoardCoords(theOtherPiece.getPriorPieceXCoordinate())][PixeltoBoardCoords(theOtherPiece.getPriorPieceYCoordinate())].setThePiece(null);//removes theOtherPiece from the array
                    GroupForPieces.getChildren().remove(theOtherPiece);//removes theOtherPiece from the Group for pieces
                    AudioPlayer.playCheckersMoveAudio();
                    break;
                case NONE:
                    Piece.illegalMove();//places the piece back in its tile (a.k.a prior X and prior Y coords)
                    AudioPlayer.playCheckersIllegalMoveAudio();
                    break;
            }
        });
        return Piece;
    }
}
