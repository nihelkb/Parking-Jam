package es.upm.pproject.parkingjam.models;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import es.upm.pproject.parkingjam.common.Pair;
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
    
    /**
     * Constructor of the class.
     */
    public Game(){
        this.levelPathFormat = Level.LEVEL_FILE_NAME_FORMAT;
        newGame();
    }

    /**
     * Method used to move a car within each level of the game.
     * @param car Car to be moved
     * @param dir Direction in which the car should move in the current level of the game.
     * @param distance Distance by which the car should be moved.
     * @return true if the movement has been done, false otherwise 
     */
    public boolean moveCar(Car car, char dir, int distance){
        if (this.finished || !level.moveCar(car, dir, distance,false, false)) return false;
        if (this.level.checkStatus()) {
            this.levelNumber++;
            this.score += level.getScore();
            levelLoad(String.format(levelPathFormat, levelNumber));
        }
        return true;
    }

    /**
     * Starts a new game from the initial level.
     */
    public final void newGame(){
        this.levelNumber = 1;
        this.score = 0;
        this.finished = false;
        logger.info(gameMarker, "A new game has started");
        levelLoad(String.format(levelPathFormat, levelNumber));
    }

    /**
     * Loads a saved game.
     * @param selectedPath The path of the selected file from which data will be retrieved.
     * @return true if the level has been loaded, false otherwise
     */
    public boolean loadGame(String selectedPath){
        if(selectedPath != null){
            this.finished = false;
            logger.info(gameMarker, "Game succesfully loaded");
            levelLoad(selectedPath);
            return true;
        }
        logger.info(gameMarker, "Load game canceled");
        return false;
    }

    /**
     * Private method used to load the game's level.
     * @param levelPath The path of the next level to load.
     */
    private void levelLoad(String levelPath){
        int lastLevelScore = 0;
        if(level != null)
            lastLevelScore = level.getScore();
        try{
            level = new Level(levelPath);
        }catch (LevelNotFoundException e) {
            logger.info(gameMarker, "Game completed");
            finished = true;
            this.score = this.score - lastLevelScore;     
        } catch (WrongLevelFormatException e) {
            logger.error(fatalMarker, String.format("Level %d could not be loaded", levelNumber), e);
            levelNumber++;
            levelLoad(String.format(levelPathFormat, levelNumber));
        }
    }

    /**
     * Method that undos a movement.
     * @return true if the undo has been done, false otherwise
     */
     public boolean undo(){
        if(isFinished()){
            logger.error(fatalMarker, "Imposible to undo the movement: finished game");
            return false;
        }else
            return level.undo();
    }

    /**
     * Method that redo a movement.
     * @return true if the redo has been done, false otherwise
     */
    public boolean redo() {
        if(isFinished()){
            logger.error(fatalMarker, "Imposible to redo the movement: finished game");
            return false;
        }else
            return level.redo();
    }

     /**
     * Method that save the game with the current board, scores and movements.
     * @param selectedPath The path of the selected file where data will be stored.
     */
    public void saveGame(String selectedPath) {
        if (selectedPath != null) {
            FileWriter writeB;
	        BufferedWriter bufferB;
            File fileoutput = new File(selectedPath);
            try {
                writeB = new FileWriter(fileoutput);
                bufferB = new BufferedWriter(writeB);
            try (PrintWriter outB = new PrintWriter(bufferB);) {
                // save the level name
                outB.append(level.getName());
                outB.append('\n');
                // save the nRows and nColumns
                outB.append(level.getBoard().getNRows() + " " + level.getBoard().getNColumns() + '\n');

                for (int i = 0; i < level.getBoard().getTiles().length; i++) {
                    for(int j = 0; j < level.getBoard().getTiles()[i].length; j++){
                        // save the board
                        outB.append(level.getBoard().getTiles()[i][j] + "");
                }
		            outB.append('\n');
                }
                // save the level number
                outB.append(this.levelNumber + "");
                outB.append('\n');
                 // save the scores
                outB.append(String.valueOf(score));
                outB.append('\n');
                outB.append(String.valueOf(level.getScore()));
                outB.append('\n');
                // save the movements
                for(int i = 0; i < level.getUndoMov().size(); i++) {
                    outB.write(level.getUndoMov().get(i).getLeft().getLeft() + " ");
                    outB.write(String.valueOf(level.getUndoMov().get(i).getLeft().getRight())+ " ");
                    outB.write(level.getUndoMov().get(i).getRight());
                    outB.write("\n");
                }
                logger.info(gameMarker, "Game succesfully saved");
            }
            } catch (IOException e) {
                logger.error(fatalMarker, "The file cannot be created/opened");
            }
            return;
        }
        logger.info(gameMarker, "Save game canceled");
    }

    /**
     * Method used to check if the game is finished.
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished() {
        return this.finished;
    }

    /**
     * Method to reset the level.
     */
    @Override
    public void reset() {
        this.level.reset();
        if(this.finished){
            levelNumber--;
            this.finished = false;
        }
    }

    /**
     * Method that prints the current level.
     */
    public void printCurrentLevel() {
        String msg = this.level.toString();
        logger.debug(gameMarker, msg);
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

     /**
     * Method that sets the score and the undo movement when a game is loaded.
     * @param selectedPath The path of the selected file from which data will be retrieved.
     */
    public void setScoreAndUndoMov(String seletedPath){
        String cadena;
        List <Pair<Pair<Character,Integer>,Character>> list = new ArrayList<>();
        Pair <Character, Integer> pair1;
        Pair <Pair<Character,Integer>,Character> pair2;
        Integer distance;
        char direction;
        char id;
        try (BufferedReader br = new BufferedReader(new FileReader(seletedPath))) {
            cadena = br.readLine();
            cadena = br.readLine();
            String[] nums = cadena.split(" ");
            int valor = Integer.parseInt(nums[0]);
            for(int i = 0 ; i < valor ; i++){
                cadena = br.readLine();
            }
            this.levelNumber = Integer.parseInt(br.readLine());
            this.score = Integer.parseInt(br.readLine());
            level.setScore(Integer.parseInt(br.readLine()));

            while ((cadena = br.readLine())!=null) {
                String[] elements = cadena.split(" ");
                direction = elements[0].charAt(0);
                distance = Integer.parseInt(elements[1]);
                id = elements[2].charAt(0);
                pair1 = new Pair<>(direction, distance);
                pair2 = new Pair<>(pair1, id);
                list.add(pair2);
            }
            level.setUndoList(list);  
        } catch(FileNotFoundException e){
            logger.error(fatalMarker, "The selected file has not been found");
        }catch (IOException e) {
            logger.error(fatalMarker, "Error while attempting to read the file");
        }
    }

    /**
    * Method that returns the id of the car that has been moved.
    * @param isUndo true if is a undo movement, false if is a redo movement 
    * @return id of the car that has been moved.
    */
    public char getUndoRedoCarId(boolean isUndo){
        return level.getUndoRedoCarId(isUndo);
    }

    // Getters and Setters
    
    public Level getLevel(){
        return level;
    }

    public int getLevelNumber(){
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber){
        this.levelNumber = levelNumber;
    }

    public void setGameFinished(boolean gameFinished){
        this.finished = gameFinished;
    }
    
    public int getScore(){
        return this.score;
    }

    @Override
    public String toString() {
        return String.format("Level %d%n%s", levelNumber, level);
    }
}