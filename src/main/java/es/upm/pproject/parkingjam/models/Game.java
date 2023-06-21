package es.upm.pproject.parkingjam.models;

import java.awt.Dimension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import es.upm.pproject.parkingjam.exceptions.LevelNotFoundException;
import es.upm.pproject.parkingjam.exceptions.WrongLevelFormatException;
import es.upm.pproject.parkingjam.interfaces.Resetable;

/**
 * Class that represents a game.
 * 
 * @author Nihel Kella Bouziane
 * @author Julio Manso Sánchez-Tornero
 * @author Álvaro Dominguez Martín
 * @author Lucía Sánchez Navidad
 * @version 1.0
 * @since 17/06/2023
 */
public class Game implements Resetable{
   
    protected Level level;
    protected int levelNumber;
    protected boolean finished;
    protected int score;

    protected String levelPathFormat;

    private static final Logger logger = LoggerFactory.getLogger(Game.class);
    private static final Marker gameMarker = MarkerFactory.getMarker("GAME");
    private static final Marker fatalMarker = MarkerFactory.getMarker("FATAL");

    public Game(){
        this.levelPathFormat = Level.LEVEL_FILE_NAME_FORMAT;
        newGame();
    }

    /**
     * Method used to move a car within each level of the game.
     * @param car Car to be moved
     * @param dir Direction in which the car should move in the current level of the game.
     * @param distance Distance by which the car should be moved.
     * @return If the movement has been done 
     */
    public boolean moveCar(Car car, char dir, int distance){
        if (this.finished || !level.moveCar(car, dir, distance,false)) return false;
        if (this.level.checkStatus()) {
            this.levelNumber++;
            this.score += level.getScore();
            levelLoad();
        }
        return true;
    }

    /**
     * Starts a new game from the initial level.
     */
    public void newGame(){
        this.levelNumber = 1;
        this.finished = false;
        this.score = 0;
        logger.info(gameMarker, "A new game has started");
        levelLoad();
    }

    /**
     * Private method used to load the game's level.
     */
    private void levelLoad(){
        try{
            level = new Level(String.format(levelPathFormat, levelNumber));
        }catch (LevelNotFoundException e){
            logger.info(gameMarker,"Game completed");
            finished = true;   
        }catch(WrongLevelFormatException e){
            logger.error(fatalMarker, String.format("Level %d could not be loaded", levelNumber), e);
            levelNumber++;
            levelLoad();
        }
    }

     public boolean undo(){
         return level.undo();
    }

    /**
     * Method used to check if the game is finished.
     * 
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished() {
        return this.finished;
    }

    @Override
    public void reset() {
        this.level.reset();
    }

    public void printCurrentLevel() {
        // if(logger.isDebugEnabled(gameMarker)){
        logger.debug(gameMarker, this.level.toString());
        // }
    }

    /**
     * Method needed by the gui to retrieve the total game score.
     * @return The game score
     */
    public int getTotalScore(){
        return this.score + level.getScore();
    }

    /**
     * Method needed by the gui to retrieve the level score.
     * @return The level score
     */
    public int getLevelScore(){
        return level.getScore();
    }

    /**
     * Method needed by the gui to retrieve the level name.
     * @return The level score
     */
    public String getLevelName(){
        return level.getName();
    }

     /**
     * Method needed by the gui to get the dimensions of the level.
     * @return The Dimension of the level
     */
    public Dimension getDimension(){
        return new Dimension(level.getBoard().getNColumns(), level.getBoard().getNRows());
    }

    public Level getLevel(){
        return level;
    }

    public void setLevel(Level level){
        this.level = level;
    }

    public int getLevelNumber(){
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber){
        this.levelNumber = levelNumber;
    }

    public boolean getGameFinished(){
        return finished;
    }

    public void setGameFinished(boolean gameFinished){
        this.finished = gameFinished;
    }
    
    public int getScore(){
        return this.score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public char id(){
        return level.id();
    }
   

    @Override
    public String toString() {
        return String.format("Level %d%n%s", levelNumber, level);
    }
}