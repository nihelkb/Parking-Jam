package es.upm.pproject.parkingjam.models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import es.upm.pproject.parkingjam.interfaces.Resetable;
import es.upm.pproject.parkingjam.common.*;
import es.upm.pproject.parkingjam.exceptions.LevelNotFoundException;
import es.upm.pproject.parkingjam.exceptions.WrongLevelFormatException;

/**
* Class that represents a level.
* @author Nihel Kella Bouziane
* @author Julio Manso Sánchez-Tornero
* @author Álvaro Dominguez Martín
* @author Lucía Sánchez Navidad
* @version 2.0
* @since 15/06/2023
*/

public class Level implements Resetable{    
    private Car redCar;
    private Parking board;
    private Map<Character, Car> vehicles;
    private List<Character> idCars;
    private String name;
    private int score;
    private List <Pair<Pair<Character,Integer>,Character>> list;
    private Deque <Pair<Pair<Character,Integer>,Character>> stackRedo;
    private String levelPath;
    private int nRows;
    private int nColumns;
    

    public static final String LEVEL_FILE_NAME_FORMAT = "src/main/resources/levels/level_%d.txt";
    
    private static final Logger logger = LoggerFactory.getLogger(Level.class);
    private static final Marker levelMarker = MarkerFactory.getMarker("LEVEL");
    private static final Marker fatalMarker = MarkerFactory.getMarker("FATAL");

    /**
    * Constructor of the class.
    * @param levelPath File path of the level file.
    * FINISHED
    */
    public Level(String levelPath) throws LevelNotFoundException, WrongLevelFormatException{
        this.vehicles = new HashMap<>();
        this.idCars = new LinkedList<>();
        this.list = new ArrayList<>();
        this.stackRedo = new ArrayDeque<>();
        this.levelPath = levelPath;

        // Fill the board reading the chars from the file.
        this.board = fillBoard(levelPath);  
        loadCars();
        
        this.score = 0;
        
        String logMsg = String.format("The new level (%s) has been successfully loaded.", name);
        logger.info(levelMarker, logMsg);
    }
    /**
    * Method that fills the board by reading the level file.
    * @return The board created with the tiles content.
    * FINISHED
     * @throws LevelNotFoundException,WrongLevelFormatException
    */
    private Parking fillBoard(String levelPath) throws LevelNotFoundException, WrongLevelFormatException {
        char[][] boardTiles = null;
        
        try ( 
            FileReader lvlFile = new FileReader(new File(levelPath)); 
            BufferedReader br = new BufferedReader(lvlFile);
           ){
            this.name = br.readLine();
            int redSize = 0;
            int exit = 0;
            String[] dimensions = br.readLine().split(" ");
            nRows = Integer.parseInt(dimensions[0]);
            nColumns = Integer.parseInt(dimensions[1]);
            boardTiles = new char[nRows][nColumns];
            String line;
            for (int i = 0; i < nRows; i++) {
                line = br.readLine();
                for (int j = 0; j < nColumns; j++) {
                    char c = line.charAt(j);
                    switch (c) {
                        case '*':
                            redSize++;
                            break;
                        case '@':
                            exit++;
                            break;
                        default:
                            break;
                    }
                    boardTiles[i][j] = c;
                }
            }
            // Level must have only one red car
            if(redSize != 2){
                throw new WrongLevelFormatException("The level must have one red car");
            }
            // Level must have only one exit
            if(exit != 1){
                throw new WrongLevelFormatException("The level must have one exit");
            }
        }catch (FileNotFoundException e) {
            throw new LevelNotFoundException(String.format("The level %s does not exist", levelPath));
        }catch (IOException e) {
           throw new WrongLevelFormatException("Error while attempting to read the file");
        } 
        return new Parking(boardTiles);
    }

    private void loadCars() throws WrongLevelFormatException{
        char[][] b = board.getTiles();
        char letter;
        for (int i = 1; i < board.getNRows() - 1; i++){
            for (int j = 1; j < board.getNColumns() - 1; j++){
                letter = b[i][j];
                if (!idCars.contains(letter) && letter != ' ' && !loadCar(b, letter, i, j)){
                    throw new WrongLevelFormatException("Vehicles must have the form 1xN or Nx1 where N ≥ 2");
                }
            }
        }
    }

    private boolean loadCar(char [][] b, char letter, int x, int y){
        int tam = 1;
        // Check car orientation
        boolean horizontal = b[x][y+1] == letter;
        int zero = 0;
        if (horizontal) {
           for (int j = y + 1; j < b[zero].length; j++) {
                if (b[x][j] == letter) {
                    tam++;
                }
            }
        }else {
            for (int i = x + 1; i < b.length; i++) {
                if (b[i][y] == letter) {
                    tam++;
                }
            }
        }
        return createCar(tam, x, y, letter, horizontal);
    }

    private boolean createCar(int tam, int x, int y, char letter, boolean horizontal) {
        if(tam >= 2) {
            boolean isRedCar = letter == '*';
            Car vehicle = new Car(x, y, letter, tam, horizontal, isRedCar);
            vehicles.put(letter, vehicle);
            idCars.add(letter);
            // Save redCar reference
            if(isRedCar){
                this.redCar = vehicle;
            }
            return true;
        }
        return false;
    }

    /**
    * Method that returns if a car has been moved succesfully.
    * @return true if the car has been moved, false otherwise. 
    * FINISHED
    */
    public boolean moveCar(Car vehicle, char direction, int distance, boolean undo, boolean redo) {
        char dir2 = ' ';
        if(redo)
            direction = change(direction);
  
        if (distance == 0) {
            return false;
        }
       
        
        // if movement is valid
        if (verifyMovement(vehicle, direction, distance)) {
            String logMsg;
            if (vehicle.isOnGoal()) {
                logger.trace(levelMarker, "Red car has reached the exit");
            }
            if (!undo || redo) {
                dir2 = change(direction);
            }
            if(!undo){
                Pair <Character,Integer> pair = new Pair<>(dir2, distance);
                Pair <Pair <Character,Integer>, Character > pair2 = new Pair<>(pair, vehicle.getId());
                list.add(pair2);
            }
            Coordinates newPos = board.updateParking(vehicle, direction, distance);
            score++;
            // Which car and how many positions
            logMsg = String.format("Car %c has been moved into tile [%d,%d]",
                    vehicle.getId(), newPos.getX(), newPos.getY());
            
            logger.trace(levelMarker, logMsg);
            return true;
        }
        return false;
    }
    
    /**
    * Method that returns if a car movement is valid.
    * @return true if the movement is valid, false otherwise.
    * FINISHED
    */
    public boolean verifyMovement(Car vehicle, char direction, int distance) {
        char[][] tiles = board.getTiles();
        int numRows = board.getNRows();
        int numColumns = board.getNColumns();

        int row = vehicle.getCurrentPositionX();
        int column = vehicle.getCurrentPositionY();
        char orientation = vehicle.getOrientation();
        boolean isRedCar = vehicle.isRedCar();

        if (orientation == 'H') {
            if (direction == 'L') {
                return verifyMovementLeft(tiles, row, column, distance, isRedCar, vehicle);
            } else if (direction == 'R') {
                return verifyMovementRight(tiles, vehicle, distance, numColumns);
            }else{
                return false;
            }
        } else if (orientation == 'V') {
            if (direction == 'U') {
                return verifyMovementUp(tiles, row, column, distance, isRedCar, vehicle);
            } else if (direction == 'D') {
                return verifyMovementDown(tiles, vehicle, distance, numRows);
            }else{
                return false;
            }
        }
        return true;
    }

    private boolean verifyMovementLeft(char[][] tiles, int row, int column, int distance, boolean isRedCar, Car vehicle) {
        if (column - distance <= 0)
            return false;
        boolean isGoal;
        for (int i = column - 1; i >= column - distance; i--) {
            if (!isEmptyTile(tiles[row][i])){
                // Look if destine tile is exit
                isGoal = isGoalTile(tiles[row][i], isRedCar);
                if(isGoal){
                    vehicle.setOnGoal(true);
                }
                return isGoal;
            }
        }
        return true;
    }

    private boolean verifyMovementRight(char[][] tiles, Car vehicle, int distance, int nColumns) {
        int row = vehicle.getCurrentPositionX();
        int column = vehicle.getCurrentPositionY();
        int vehicleSize = vehicle.getLength();
        boolean isRedCar = vehicle.isRedCar();

        if (column + vehicleSize + distance - 1 >= nColumns)
            return false;
        boolean isGoal;
        for (int i = column + vehicleSize; i <= column + vehicleSize + distance - 1; i++) {
            if (!isEmptyTile(tiles[row][i])){
                // Look if destine tile is exit
                isGoal = isGoalTile(tiles[row][i], isRedCar);
                if(isGoal){
                    vehicle.setOnGoal(true);
                }
                return isGoal;
            }
        }
        return true;
    }

    private boolean verifyMovementUp(char[][] tiles, int row, int column, int distance, boolean isRedCar, Car vehicle) {
        if (row - distance <= 0)
            return false;
        boolean isGoal;
        for (int i = row - 1; i >= row - distance; i--) {
            if (!isEmptyTile(tiles[i][column])){
                // Look if destine tile is exit
                isGoal = isGoalTile(tiles[i][column], isRedCar);
                if(isGoal){
                    vehicle.setOnGoal(true);
                }
                return isGoal;
            }    
        }
        return true;
    }

    private boolean verifyMovementDown(char[][] tiles, Car vehicle, int distance, int nRows) {
        int row = vehicle.getCurrentPositionX();
        int column = vehicle.getCurrentPositionY();
        int vehicleSize = vehicle.getLength();
        boolean isRedCar = vehicle.isRedCar();

        if (row + vehicleSize + distance - 1 >= nRows)
            return false;
        boolean isGoal;
        for (int i = row + vehicleSize; i <= row + vehicleSize + distance - 1; i++) {
            if (!isEmptyTile(tiles[i][column])){
                // Look if destine tile is exit
                isGoal = isGoalTile(tiles[i][column], isRedCar);
                if(isGoal){
                    vehicle.setOnGoal(true);
                }
                return isGoal;
            }
        }
        return true;
    }

    private boolean isEmptyTile(char tile) {
        return tile == ' ';
    }

    private boolean isGoalTile(char tile, boolean isRedCar) {
        return tile == '@' && isRedCar;
    }

    /**
    * Method that returns the red car coordinates.
    * @return Current coordinates of the red car position.
    * FINISHED
    */
    public Coordinates getRedCarCoords(){
        return redCar.getCurrentPos();
    }
    
    /**
    * Method that checks if a level is completed.
    * @return true if the level has been completed, false otherwise.
    * FINISHED
    */
    public boolean checkStatus() {
        return redCar.isOnGoal();
    }

    /**
     * Method to reset a level.
     * FINISHED
     */
    @Override
    public void reset(){
        char c;
        for (int i = 0; i < idCars.size(); i++){
            c = idCars.get(i);
            vehicles.get(c).reset();
        }
        list.clear();
        stackRedo.clear();
        score = 0;
        try {
            this.board = fillBoard(levelPath);
        } catch (LevelNotFoundException | WrongLevelFormatException e) {
            e.printStackTrace();
        } 
        logger.info(levelMarker, "The current level has been reset to its initial state.");
    }

    public char id(boolean isUndo){
        Pair<Pair<Character,Integer>, Character> pair;
        if(isUndo){
            pair = list.get(list.size()-1);
            list.remove(pair);
        }
        else{
            pair = stackRedo.pop();
        }
         return pair.getRight();
    }

    public char change(char c){
        char char2 = ' ';
        if(c == 'D'){
             char2 = 'U';
        }
        if(c == 'R'){
            char2 = 'L';
        }
        if(c == 'U'){
            char2 = 'D';
        }
        if(c == 'L'){
            char2 = 'R';
        }
        return char2;

    }

    public boolean undo(){
        if(list.isEmpty()){
             logger.error(fatalMarker, "Imposible to undo the movement");
             return false;
        }
        else{
            Pair<Pair<Character,Integer>, Character> pair = list.get(list.size()-1);
            moveCar(vehicles.get(pair.getRight()), pair.getLeft().getLeft(),pair.getLeft().getRight(),true, false);
            stackRedo.push(pair); 
            return true;
        }
       
    }

    public boolean redo(){
        if(stackRedo.isEmpty()){
             logger.error(fatalMarker, "Imposible to redo the movement");
             return false;
        } else{
            Pair<Pair<Character,Integer>, Character> pair = stackRedo.peek();
            moveCar(vehicles.get(pair.getRight()), pair.getLeft().getLeft(),pair.getLeft().getRight(),false, true);
            return true;
        }
    }

    public void saveGame(int scoreGlobal) throws IOException {
        // Crear un objeto JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        
        // Establecer el filtro de extensión del archivo (opcional)
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt");
        fileChooser.setFileFilter(filter);
        
        // Mostrar el diálogo de selección de archivo
        int result = fileChooser.showOpenDialog(null);
        java.io.File selectedFile = null; 
        if (result == JFileChooser.APPROVE_OPTION) {
            // Obtener el archivo seleccionado
            selectedFile = fileChooser.getSelectedFile();
            FileWriter writeB;
	        BufferedWriter bufferB;
            File fileoutput = new File(selectedFile.getAbsolutePath());
            writeB = new FileWriter(fileoutput);
		    bufferB = new BufferedWriter(writeB);
            try (PrintWriter outB = new PrintWriter(bufferB);) {
                outB.append(name);
                outB.append('\n');
                outB.append(nRows + " " + nColumns + '\n');

                for (int i = 0; i < board.getTiles().length; i++) {
                    for(int j = 0; j < board.getTiles()[i].length; j++){
                        outB.append(board.getTiles()[i][j] + "");
                }
		            outB.append('\n');
                }
                outB.append(String.valueOf(scoreGlobal));
                outB.append('\n');
                outB.append(String.valueOf(score));
                outB.append('\n');
                for(int i = 0; i < list.size(); i++) {
                    outB.write(list.get(i).getLeft().getLeft() + " ");
                    outB.write(String.valueOf(list.get(i).getLeft().getRight())+ " ");
                    outB.write( list.get(i).getRight());
                    outB.write("\n");
                }
            }
        }

    }

    @Override
    public String toString() {
        return "\n" + board.toString();
    }
    
    // Getters and setters for xml binding
    
    public Car getRedCar() {
        return redCar;
    }
    
    public void setRedCar(Car redCar) {
        this.redCar = redCar;
    }
    
    public Parking getBoard() {
        return board;
    }
    
    public void setBoard(Parking board) {
        this.board = board;
    }

    public void setUndoList( List <Pair<Pair<Character,Integer>,Character>> list){
        this.list = list;
    }
    
    public Map<Character, Car> getVehiclesMap() {
        return vehicles;
    }
    
    public void setBoxList(Map<Character, Car> vehicles) {
        this.vehicles = vehicles;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }


}