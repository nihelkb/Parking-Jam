package es.upm.pproject.parkingjam.models;

import es.upm.pproject.parkingjam.common.Coordinates;
import es.upm.pproject.parkingjam.interfaces.Resetable;

/**
 * Class that represents a car.
 * 
 * @author Nihel Kella Bouziane
 * @author Julio Manso Sánchez-Tornero
 * @author Álvaro Dominguez Martín
 * @author Lucía Sánchez Navidad
 * @version 2.0
 * @since 15/06/2023
 */
public class Car implements Resetable {

    private char id;
    private int length;
    private char orientation;

    private int initialStateX;
    private int initialStateY;
    private int positionX;
    private int positionY;

    private boolean redCar;
    private boolean isOnGoal;

    /**
    * Constructor of the class.
    * @param initialStateX initial X position of the car.
    * @param initialStateY initial Y position of the car.
    * @param id the id of the car that is going to be created.
    * @param length number of tiles that the car will be holding.
    * @param horizontal if the orientation is vertical.
    * @param redCar if the car is the target one (red car).
    */
    public Car(int initialStateX, int initialStateY, char id, int length, boolean horizontal, boolean redCar) {
        this.id = id;
        this.length = length;
        this.orientation = horizontal ? 'H' : 'V';
        this.redCar = redCar;

        this.initialStateX = initialStateX;
        this.initialStateY = initialStateY;
        this.positionX = initialStateX;
        this.positionY = initialStateY;
    }

    /**
    * Method that obtains the new position of the car when moving.
    * @param direction the direction where the car is wanted to be moved.
    * @param distance how many positions the car is wanted to be moved.
    * @return the new position of the car if the move is finally effective
    */
    public Coordinates move(char direction, int distance){
        if(this.orientation == 'H'){
            if(direction == 'L'){
                this.positionY = this.positionY - distance;
            }
            else if(direction == 'R'){
                this.positionY = this.positionY + distance;
            }
        }
        else if(this.orientation == 'V'){
            if(direction == 'U'){
                this.positionX = this.positionX - distance;
            }
            else if(direction == 'D'){
                this.positionX = this.positionX + distance;
            }
        }
        return new Coordinates(this.positionX, this.positionY);
    }

    /**
    * Method that checks if the car reached the exit.
    * @return true if the car reached the exit, false otherwise
    */
    public boolean isOnGoal() {
        return this.isOnGoal && this.redCar;
    }

    /**
    * Method that sets if the car is on goal or not.
    * @param isOnGoal true if the car reached the exit, false otherwise
    */
    public void setOnGoal(boolean isOnGoal) {
        this.isOnGoal = isOnGoal;
    }

    // Getters and setters

    public char getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public char getOrientation() {
        return orientation;
    }
  
    public boolean isRedCar() {
        return redCar;
    }

    public Coordinates getCurrentPos() {
        return new Coordinates(this.positionX, this.positionY);
    }

    public int getInitialStateX() {
        return initialStateX;
    }

    public void setInitialStateX(int initialStateX) {
        this.initialStateX = initialStateX;
    }

    public int getInitialStateY() {
        return initialStateY;
    }

    public void setInitialStateY(int initialStateY) {
        this.initialStateY = initialStateY;
    }

    public int getCurrentPositionX() {
        return positionX;
    }

    public void setCurrentPositionX(int currentPositionX) {
        this.positionX = currentPositionX;
    }

    public int getCurrentPositionY() {
        return positionY;
    }

    public void setCurrentPositionY(int currentPositionY) {
        this.positionY = currentPositionY;
    }

    @Override
    public void reset() {
        this.positionX = this.initialStateX;
        this.positionY = this.initialStateY;
    }

    @Override
    public String toString() {
        return id + " " + length + " " + orientation + " " + positionX + " " + positionY + " " + redCar;
    }
}