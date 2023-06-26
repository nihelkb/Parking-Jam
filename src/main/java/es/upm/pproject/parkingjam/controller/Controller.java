package es.upm.pproject.parkingjam.controller;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import es.upm.pproject.parkingjam.common.Coordinates;
import es.upm.pproject.parkingjam.models.Car;
import es.upm.pproject.parkingjam.models.Game;
import es.upm.pproject.parkingjam.view.MainFrame;

/**
 * Class responsible for the application's controller.
 * 
 * @author Nihel Kella Bouziane
 * @author Julio Manso Sánchez-Tornero
 * @author Lucía Sánchez Navidad
 * @version 1.1
 * @since 21/06/2023
 */
public class Controller {

    private MainFrame gui;
    private Game game;

    public Controller(){
        gui = new MainFrame(this);
        game = new Game();
        gui.init();
    }

    /**
    * Method to obtain the dimensions of the level.
    * @return Dimension of the level
    */
    public Dimension getLevelDimension() {
        return game.getDimension();
    }

    public char[][] getLevelBoard() {
        return game.getLevel().getBoard().getTiles();
    }

    public boolean canMove(char idCar, char dir, int distance) {
        Car car = game.getLevel().getVehiclesMap().get(idCar);
        return game.getLevel().verifyMovement(car, dir, distance);
    }

    public void move(char idCar, char dir, int distance) {
        Car car = game.getLevel().getVehiclesMap().get(idCar);
        String lvlName = game.getLevelName();
        game.moveCar(car, dir, distance);
        if (!game.getLevelName().equals(lvlName)){
            gui.showLevel();
        }else{
            gui.repaintStats();
        }
    }

    public Coordinates getCarPosition(char idCar) {
        return game.getLevel().getVehiclesMap().get(idCar).getCurrentPos();
    }

    public List<Character> getCarIds() {
        Map<Character,Car> cars = game.getLevel().getVehiclesMap();
        return new LinkedList<>(cars.keySet());
    }

    public boolean isCarHorizontal(char idCar) {
        return game.getLevel().getVehiclesMap().get(idCar).getOrientation() == 'H';
    }

    public int getCarLength(char idCar) {
        return game.getLevel().getVehiclesMap().get(idCar).getLength();
    }

    public boolean isRedCar(char idCar) {
        return game.getLevel().getVehiclesMap().get(idCar).isRedCar();
    }

    public String getLevelName(){
        return game.getLevelName();
    }

    public int getGameScore(){
        return game.getTotalScore();
    }

    public int getLevelScore(){
        return game.getLevelScore();
    }
    
    public void undo(){
        if(game.undo()){
            char id = game.id(true);
            gui.undoRedo(id);
        }
    }

    public void redo(){
        if(game.redo()){
            char id = game.id(false);
            gui.undoRedo(id);
        }
    }

    public void newGame(){
        game.newGame();
        gui.init();
    }

    public void resetLevel(){
        game.reset();
        gui.showLevel();
    
    }

    public void saveGame(){
        game.saveGame();
    }
}