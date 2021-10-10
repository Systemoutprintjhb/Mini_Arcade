package com.mycompany.bergl_j_finalproject;
//Author: Jefrey Bergl
//Course: CTIS 310
//Instructor: Professor Chafic Bou-Saba

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioPlayer {

    private static Media sound;
    private static MediaPlayer player;
    private static String file;

    public static void playPieceMoveAudio() {
        file = "C:\\Users\\babla\\Documents\\Music\\ButtonClick.mp3";//get file path
        sound = new Media(new File(file).toURI().toString());//from in class example
        player = new MediaPlayer(sound);
        player.play();//use the media player to play the sound (same for all other methods)
    }

    public static void playTicTacToeWinAudio() {
        file = "C:\\Users\\babla\\Downloads\\little_robot_sound_factory_Jingle_Win_Synth_00.mp3";
        sound = new Media(new File(file).toURI().toString());
        player = new MediaPlayer(sound);
        player.play();
    }
    
    public static void playTicTacToeTieAudio() {
        file = "C:\\Users\\babla\\Downloads\\multimedia_game_sound_synth_tone_bold_fail_52993.mp3";
        sound = new Media(new File(file).toURI().toString());
        player = new MediaPlayer(sound);
        player.play();
    }
    
    public static void playWingFlapAudio() {
        file = "C:\\Users\\babla\\Downloads\\zapsplat_foley_flag_handling_movement_single_shake_flap_002_61727.mp3";
        sound = new Media(new File(file).toURI().toString());
        player = new MediaPlayer(sound);
        player.play();
    }
    
    public static void playFlappyBirdFailAudio() {
        file = "C:\\Users\\babla\\Downloads\\zapsplat_multimedia_game_sound_fun_arcade_organ_short_negative_fail_lose_002_54275.mp3";
        sound = new Media(new File(file).toURI().toString());
        player = new MediaPlayer(sound);
        player.play();
    }
    
    public static void playCheckersMoveAudio() {
        file = "C:\\Users\\babla\\Downloads\\zapsplat_toy_board_game_token_plastic_move_on_board_x1.mp3";
        sound = new Media(new File(file).toURI().toString());
        player = new MediaPlayer(sound);
        player.play();
    }
    
    public static void playCheckersIllegalMoveAudio() {
        file = "C:\\Users\\babla\\Documents\\Music\\ClearButtonClick.mp3";
        sound = new Media(new File(file).toURI().toString());
        player = new MediaPlayer(sound);
        player.play();
    }
    
    public static void playCheckersKingAudio() {
        file = "C:\\Users\\babla\\Downloads\\zapsplat_multimedia_game_sound_fun_arcade_organ_short_positive_level_complete_54280.mp3";
        sound = new Media(new File(file).toURI().toString());
        player = new MediaPlayer(sound);
        player.play();
    }
    
    public static void playPongBounceAudio() {
        file = "C:\\Users\\babla\\Downloads\\sport_table_tennis_ping_pong_ball_bounce_hit_table_002.mp3";
        sound = new Media(new File(file).toURI().toString());
        player = new MediaPlayer(sound);
        player.play();
    }
    
    public static void playPongWinAudio() {
        file = "C:\\Users\\babla\\Downloads\\little_robot_sound_factory_Jingle_Win_Synth_03.mp3";
        sound = new Media(new File(file).toURI().toString());
        player = new MediaPlayer(sound);
        player.play();
    }
    
    public static void playPongLoseAudio() {
        file = "C:\\Users\\babla\\Downloads\\zapsplat_multimedia_game_lose_negative_001.mp3";
        sound = new Media(new File(file).toURI().toString());
        player = new MediaPlayer(sound);
        player.play();
    }
}
