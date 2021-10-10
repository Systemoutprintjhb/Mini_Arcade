package com.mycompany.bergl_j_finalproject;
/*
Author: Jefrey Bergl
Instructor: Professor Bou-Saba
Class: CTIS 310
*/
public enum PieceColor {// used to make sure piece are oriented correctly (i.e. pieces can't move backwards), I learned about enumerations from here: https://www.geeksforgeeks.org/enum-in-java/
    GREEN(1), RED(-1), GREENKING(2), REDKING(2);

    int MoveUpOrDown;

    PieceColor(int MoveUpOrDown) {//basic constructor
        this.MoveUpOrDown = MoveUpOrDown;
    }
}
