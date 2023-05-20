package es.upm.pproject.parkingjam.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class Level {
    public Board board;
    private File fich;
    private FileReader fr;
    private BufferedReader br;
    private LinkedList<Vehicle> vehicles;
    private LinkedList<String> idCars;
    
    public Level() throws IOException{
        board = new Board();

        vehicles = new LinkedList<Vehicle>();
        idCars = new LinkedList<String>();


    }

    public void readFile(){
        try{
            fich = new File("C:\\Users\\Julio\\Desktop\\nivel.txt");
            fr = new FileReader (fich);
            br = new BufferedReader(fr);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadLevel() throws IOException{
        readFile();
        board.setBoard(board.fillBoard(br));
        loadCars(board.getBoard());
    }

    public void loadCars(char [][] b){
        int i = 1; //filas
        int j = 1; // columnas
        int tam = 0;
        char direction;
        char letter = ' ';
        boolean isCorrect = true;
        while(i < board.getNRows()-1 && isCorrect ){
            j = 1;
            while( j < board.getnColumns()-1 && isCorrect ){
                letter = b[i][j];
                System.out.println(idCars.contains(letter+""));
                if(!idCars.contains(letter+"") && letter != ' '){
                    System.out.println(idCars.contains(letter+""));
                    System.out.println(letter);
                    if(!loadCar(b, letter, i, j)){
                        isCorrect = false;
                    }
                }
                j++;

            }
            i++;
        }

    }


    public boolean loadCar(char [][] b, char letter, int x, int y){
        int i = x;
        int j = y;
        boolean end = false;
        boolean vertical = false;
        int tam = 0;
        char direction ='H';
        boolean redCar = false;
        if(letter == '*')
            redCar = true;
        while(i < board.getNRows()-1 && !end){
            while( j < board.getnColumns() && !vertical && !end){
                if(b[i][j] == letter){
                    tam++;
                }
                else if(b[i][j] != letter && tam == 1){
                    vertical = true;
                }
                if(tam > 1 && b[i][j] != letter){
                    end = true;
                }
                j++;
            }
            if(!end){
                j = y;
                i++;
                if(b[i][j] == letter){
                    tam++;
                }
                else if(b[i][j] != letter ){
                    end = true;
                }
            }

            
        }
        if(tam >=2) {
            if(vertical){
                direction = 'V';
            }
            Vehicle vehicle = new Vehicle(letter+"", tam, direction, x, y, redCar);
            ;vehicles.add(vehicle);
            idCars.add(letter+"")
        }
        else{
            return false;

        }

        return true;
    }
    
    public static void main(String[] args) throws IOException{
        Level uno = new Level();
        uno.loadLevel();
        for(int i = 0; i < uno.vehicles.size(); i++ ){
            System.out.println(uno.vehicles.get(i));
        }
        for (int x=0; x < uno.board.getBoard().length; x++) {
            System.out.print("|");
            for (int y=0; y < uno.board.getBoard().length; y++) {
              System.out.print (uno.board.getBoard()[x][y]);
              if (y!=uno.board.getBoard()[x].length-1) System.out.print("\t");
            }
            System.out.println("|");
          }
    }
}

