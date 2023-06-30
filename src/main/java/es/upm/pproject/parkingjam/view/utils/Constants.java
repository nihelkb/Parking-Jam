package es.upm.pproject.parkingjam.view.utils;

import java.awt.Color;
import java.awt.Font;

public class Constants {

    private Constants(){ }

    public static final String IMG_PATH = "src/main/resources/img/";
    public static final String BACKGROUND_PATH = "src/main/resources/img/backgrounds/";
    public static final String CARS_PATH = "src/main/resources/img/cars/";
    public static final String WALL_PATH = "src/main/resources/img/wall/";

    public static final String ICON = IMG_PATH + "carIcon.png";
    public static final String PARKING_BACKGROUND = BACKGROUND_PATH + "parking_background.jpg";
    public static final String BACKGROUND = BACKGROUND_PATH + "background.png";
    public static final String RED_CAR = CARS_PATH + "redcar.png";
    public static final String WALL = WALL_PATH + "wall.png";

    public static final int TILE_SIZE = 50;
    public static final int SCREEN_WIDTH = 900;
    public static final int SCREEN_HEIGHT = 700 + TILE_SIZE;
    public static final int STATS_WIDTH = SCREEN_WIDTH;
    public static final int STATS_HEIGHT = TILE_SIZE;

    public static final Color STATS_COLOR = Color.WHITE;

    public static final Font LEVEL_FONT = new Font("Abadi MT Condensed Extra Bold", Font.BOLD, 20);
}