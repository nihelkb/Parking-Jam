package es.upm.pproject.parkingjam.controller;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import es.upm.pproject.parkingjam.common.Coordinates;
import es.upm.pproject.parkingjam.interfaces.IController;
import es.upm.pproject.parkingjam.models.Car;
import es.upm.pproject.parkingjam.models.Game;
import es.upm.pproject.parkingjam.view.MainFrame;
import es.upm.pproject.parkingjam.view.utils.MusicPlayer;

/**
 * Class responsible for the application's controller.
 * 
 * @author Nihel Kella Bouziane
 * @author Julio Manso Sánchez-Tornero
 * @author Lucía Sánchez Navidad
 * @version 1.1
 * @since 21/06/2023
 */
public class Controller implements IController{

    private MainFrame gui;
    private Game game;
    private MusicPlayer musicPlayer;

    /**
    * Constructor of the class.
    */
    public Controller(){
        this.musicPlayer = MusicPlayer.getInstance();
        this.gui = new MainFrame(this);
        this.game = new Game();
        gui.init();   
    }

    /**
    * Method to obtain the dimensions of the level.
    * @return Dimension of the level
    */
    public Dimension getLevelDimension() {
        return game.getDimension();
    }

    /**
     * Method to obtain the board tiles of the level.
     * @return Board tiles
     */
    public char[][] getLevelBoard() {
        return game.getLevel().getBoard().getTiles();
    }

    /**
    * Checks that the car selected can be moved in the direction selected the distance selected
    * @param idCar of the car
    * @param dir the car will be moved
    * @param distance the car will be moved
    * @return true if the car can move, false otherwise
    */
    public boolean canMove(char idCar, char dir, int distance) {
        Car car = game.getLevel().getVehiclesMap().get(idCar);
        return game.getLevel().verifyMovement(car, dir, distance);
    }

    /**
    * Moves the selected car
    * @param idCar of the car
    * @param dir the car will be moved
    * @param distance the car will be moved
    */
    public void move(char idCar, char dir, int distance) {
        Car car = game.getLevel().getVehiclesMap().get(idCar);
        String lvlName = game.getLevelName();
        game.moveCar(car, dir, distance);
        // pasa al siguiente nivel
        if (!game.getLevelName().equals(lvlName)){
            if(!gui.isGameMuted())
                playLevelSound();
            gui.showLevel();
            gui.repaintStats();
        }
        gui.repaintStats();
        if(game.isFinished()){
            if(!gui.isGameMuted())
                playWinSound();
            gui.showCongratsMsg(game.getTotalScore());   
        }
    }

    /**
     * Method to obtain the current position of a car
     * @param idCar The car id whose position we want to know
     * @return The coordinates of the current position of the car
     */
    public Coordinates getCarPosition(char idCar) {
        return game.getLevel().getVehiclesMap().get(idCar).getCurrentPos();
    }

    /**
     * Method to obtain a list of the stored car ids
     * @return The list of the car ids
     */
    public List<Character> getCarIds() {
        return new LinkedList<>(game.getLevel().getVehiclesMap().keySet());
    }

    /**
     * Checks if the car is horizontal
     * @param idCar The car id whose orientation is going to be checked
     * @return true if the car is horizontal, false otherwise
     */
    public boolean isCarHorizontal(char idCar) {
        return game.getLevel().getVehiclesMap().get(idCar).getOrientation() == 'H';
    }

    /**
     * Method to obtain the length of the car
     * @param idCar The car id whose length we want to know 
     * @return length of the car
     */
    public int getCarLength(char idCar) {
        return game.getLevel().getVehiclesMap().get(idCar).getLength();
    }

     /**
     * Checks if the car selected is the red car
     * @param idCar The car id we want to check
     * @return true if the car is the red car, false otherwise
     */
    public boolean isRedCar(char idCar) {
        return game.getLevel().getVehiclesMap().get(idCar).isRedCar();
    }

    /**
     * Method to obtain the name of the current level in the game
     * @return The level name
     */
    public String getLevelName(){
        return game.getLevelName();
    }

    /**
     * Method to obtain the score of the game
     * @return The total score of the game
     */
    public int getGameScore(){
        return game.getTotalScore();
    }

    /**
     * Method to obtain the score of the current level
     * @return The current level score
     */
    public int getLevelScore(){
        return game.getLevelScore();
    }

    /**
     * Method used to revert the last movement made
     */
    public void undo(){
        if(game.undo()){
            char id = game.getUndoRedoCarId(true);
            gui.undoRedo(id);
        }
    }

    /**
     * Method used to revert the last undone movement made
     */
    public void redo(){
        if(game.redo()){
            char id = game.getUndoRedoCarId(false);
            gui.undoRedo(id);
        }
    }

    /**
     * Method used to create a new game
     */
    public void newGame(){
        game.newGame();
        gui.init();
    }

    /**
     * Method used to load a game previously saved
     */
    public void loadGame(){
        String selectedPath = gui.openFileChooser();
        if(!game.loadGame(selectedPath)) return;
        game.setScoreAndUndoMov(selectedPath);
        if(!gui.isGameMuted()){
            restartBackgroundMusic();
            playNewGameSound();
        }
        gui.init(); 
    }

    /**
     * Method used to reset a level
     */
    public void resetLevel(){
        game.reset();
        gui.showLevel();
    }

    /**
     * Method used to save a game in an specific place of the computer
     */
    public void saveGame(){
        if(game.isFinished()){
            gui.cannotSaveGame();
            return;
        }
        game.saveGame(gui.saveFileChooser());
    }

    /**
     * Method used to start the music background
     */
    public void startBackgroundMusic(){
        musicPlayer.playBackgroundMusic();
    }

    /**
     * Method used to pause the music background
     */
    public void pauseBackgroundMusic(){
        musicPlayer.pauseBackgroundMusic();
    }

    /**
     * Method used to resume the music background
     */
    public void resumeBackgroundMusic(){
        musicPlayer.resumeBackgroundMusic();
    }

    /**
     * Method used to restart the music background
     */
    public void restartBackgroundMusic(){
        musicPlayer.restartBackgroundMusic();
    }

    /**
     * Method used to make a sound when moving a car 
     */
    public void playMoveCarSound(){
        musicPlayer.moveCarSound();
    }

    /**
     * Method used to play a different sound 
     */
    public void playNewGameSound(){
        musicPlayer.newGameSound();
    }

    /**
     * Method used to make a sound when clicking the botton reset 
     */
    public void playResetSound(){
        musicPlayer.resetSound();
    }

    /**
     * Method used to make a sound when clicking the botton undo 
     */
    public void playUndoSound(){
        musicPlayer.undoSound();
    }

    /**
     * Method used to play a default sound
     */
    public void playDefaultSound(){
        musicPlayer.defaultSound();
    }

    /**
     * Method used to make a sound when playing a level
     */
    public void playLevelSound(){
        musicPlayer.levelSound();
    }

    /**
     * Method used to make a sound when winning 
     */
    public void playWinSound(){
        musicPlayer.gameSound();
    }

    /**
     * Method used to mute all the sounds of the game 
     */
    public boolean isGameMuted(){
        return gui.isGameMuted();
    }
}