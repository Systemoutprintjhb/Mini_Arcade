package com.mycompany.bergl_j_finalproject;
/*
Author: Jefrey Bergl
Instructor: Professor Bou-Saba
Class: CTIS 310
*/
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {//driver program

    @Override
    public void start(Stage stage) {
        MainMenu theMenu = new MainMenu(stage);
    }

    public static void main(String[] args) {
        launch();
    }

}