package es.upm.pproject.parkingjam.view.utils;

import java.awt.Color;
import java.awt.Font;

public class Constants {

    private Constants(){ }

    private static final String RESOURCES_PATH = "src/main/resources/";
    private static final String IMG_PATH = RESOURCES_PATH + "img/";
    public static final String BACKGROUND_PATH = RESOURCES_PATH + "img/backgrounds/";
    public static final String CARS_PATH = RESOURCES_PATH + "img/cars/";
    public static final String WALL_PATH = RESOURCES_PATH + "img/wall/";
    public static final String AUDIO_PATH = RESOURCES_PATH + "sounds/";

    public static final String APP_ICON = IMG_PATH + "carIcon.png";
    public static final String CONGRATS_ICON = IMG_PATH + "congrats.png";
    public static final String BACKGROUND = BACKGROUND_PATH + "background.png";
    public static final String RED_CAR = CARS_PATH + "redcar.png";
    public static final String WALL = WALL_PATH + "wall.png";

    public static final String BACKGROUND_MUSIC = AUDIO_PATH + "background.wav";
    public static final String MOVE_CAR_SOUND = AUDIO_PATH + "move_car.wav";
    public static final String NEW_GAME_SOUND = AUDIO_PATH + "new_game.wav";
    public static final String RESET_SOUND = AUDIO_PATH + "reset.wav";
    public static final String UNDO_SOUND = AUDIO_PATH + "undo.wav";
    public static final String DEFAULT_SOUND = AUDIO_PATH + "default.wav";
    public static final String LEVEL_SOUND = AUDIO_PATH + "level_completed.wav";
    public static final String GAME_SOUND = AUDIO_PATH + "game_completed.wav";

    public static final int TILE_SIZE = 50;
    public static final int SCREEN_WIDTH = 900;
    public static final int SCREEN_HEIGHT = 700 + TILE_SIZE;
    public static final int STATS_WIDTH = SCREEN_WIDTH;
    public static final int STATS_HEIGHT = TILE_SIZE;

    public static final Color STATS_COLOR = Color.WHITE;

    public static final Font LEVEL_FONT = new Font("Abadi MT Condensed Extra Bold", Font.BOLD, 20);

    public static final String HELP = "The objective of the game is to maneuver the red car out of the parking lot.\n"
    + "Other vehicles will be blocking the way, and you need to strategically move them to create an open path for the red car.\n\n"
    + "Rules:\n"
    + "- Vehicles can only move in the direction they are initially oriented.\n"
    + "- Vehicles cannot overlap or move through each other.\n"
    + "- You can only move vehicles that have empty spaces adjacent to them.\n\n"
    + "Enjoy the game and have fun!";
    
    public static final String WRONG_FILE = "The selected file is not a text file (.txt).";
    public static final String GAME_FINISHED_SAVE = "Save game not allowed: the game has been completed.";
    public static final String ERROR_SAVING = "Error while saving game";

    public static final String HELP_MSG_LOGGER = "Help dialog displayed";

}