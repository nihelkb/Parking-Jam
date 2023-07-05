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

    public static final int TILE_SIZE = 50;
    public static final int SCREEN_WIDTH = 900;
    public static final int SCREEN_HEIGHT = 700 + TILE_SIZE;
    public static final int STATS_WIDTH = SCREEN_WIDTH;
    public static final int STATS_HEIGHT = TILE_SIZE;

    public static final Color STATS_COLOR = Color.WHITE;

    public static final Font LEVEL_FONT = new Font("Abadi MT Condensed Extra Bold", Font.BOLD, 20);
}