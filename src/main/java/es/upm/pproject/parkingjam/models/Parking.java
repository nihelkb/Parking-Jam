package es.upm.pproject.parkingjam.models;

import es.upm.pproject.parkingjam.common.Coordinates;

/**
 * Class that represents a board.
 * 
 * @author Nihel Kella Bouziane
 * @author Julio Manso Sánchez-Tornero
 * @author Álvaro Dominguez Martín
 * @author Lucía Sánchez Navidad
 * @version 2.0
 * @since 15/06/2023
 */
public class Parking{
    
    private char[][] tiles;
    private int nRows;
    private int nColumns;
    /**
    * Constructs a parking.
    */
    public Parking(char [][] board) {
        this.tiles = board;
        this.nRows = board.length;
        this.nColumns = board[0].length;
    }

    /**
    * Method that returns the matrix.
    * @return the matrix. 
    */
    public char[][] getTiles () {
        return this.tiles.clone();
    }
    /**
    * Method that returns the number of rows.
    * @return the number of rows.
    */
    public int getNRows () {
        return nRows;
    }

    /**
    * Method that returns the number of columns.
    * @return the number of columns.
    */
    public int getNColumns () {
        return nColumns;
    }

    /**
    * Method that updates the parking.
    * @param the car.
    * @param the direction that the car has to move.
    * @param the distance that the car has to move.
    * @return the newpostion.
    */
    public Coordinates updateParking(Car vehicle, char direction, int distance) {
        deleteCar(vehicle);
        Coordinates newPosition = vehicle.move(direction, distance);
        insertCar(vehicle);
        return newPosition;
    }

    /**
    * Method that duplicates the parking.
    * @return the new parking.
    */
    public Parking duplicate() {
        char[][] clonedTiles = new char[nRows][nColumns];
        for (int i = 0; i < nRows; i++) {
            clonedTiles[i] = tiles[i].clone();
        }
        return new Parking(clonedTiles);
    }

    /**
    * Method that insert a car in the parking.
    * @param the car.
    */
    private void insertCar(Car vehicle) {
        int posX = vehicle.getCurrentPositionX();
        int posY = vehicle.getCurrentPositionY();
        int length = vehicle.getLength();
        char id = vehicle.getId();
        char orientation = vehicle.getOrientation();
        if (orientation == 'H') {
            for (int j = posY; j < posY + length; j++) {
                tiles[posX][j] = id;
            }
        } else if (orientation == 'V') {
            for (int i = posX; i < posX + length; i++) {
                tiles[i][posY] = id;
            }
        }
    }

    /**
    * Method that deletes a car in the parking.
    * @param the car.
    */
    private void deleteCar(Car vehicle) {
        char orientation = vehicle.getOrientation();
        int length = vehicle.getLength();
        int posX = vehicle.getCurrentPositionX();
        int posY = vehicle.getCurrentPositionY();
        if (orientation == 'H') {
            for (int j = posY; j < length + posY; j++) {
                tiles[posX][j] = ' ';
            }
        } else if (orientation == 'V') {
            for (int i = posX; i < posX + length; i++) {
                tiles[i][posY] = ' ';
            }
        }
    }
    
     /**
    * Method toString.
    */
    @Override
    public String toString() {
        StringBuilder boardRep = new StringBuilder();
        int zero = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[zero].length; j++) {
                boardRep.append(tiles[i][j]);
            }
            boardRep.append('\n');
        }
        return boardRep.toString();
    }
}