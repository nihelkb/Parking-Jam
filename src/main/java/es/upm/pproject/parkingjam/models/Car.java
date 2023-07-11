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
    * Constructs a Car.
    */
    public Car(int initialStateX, int initialStateY, char id, int length, boolean vertical, boolean redCar) {
        this.id = id;
        this.length = length;
        this.orientation = vertical ? 'H' : 'V';
        this.redCar = redCar;

        this.initialStateX = initialStateX;
        this.initialStateY = initialStateY;
        this.positionX = initialStateX;
        this.positionY = initialStateY;
    }

    // getters and setters.

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

    /**
    * Method that move a car into a direction and with a determinated distance.
    * @param the direction that the car has to move.
    * @param the distance that the car has to move
    * @return the coordinates of the car after the move.
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
    * Method that returns true if the car is red and is at the goal.
    * @return true if the car is red and is at the goal.
    */
    public boolean isOnGoal() {
        return this.isOnGoal && this.redCar;
    }

    public void setOnGoal(boolean isOnGoal) {
        this.isOnGoal = isOnGoal;
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