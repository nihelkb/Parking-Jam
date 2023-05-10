package es.upm.pproject.parkingjam.models;
import java.io.*;

public class Board {
    private char[][] board;
    private int nRows, nColumns;
    private File fich;
    private FileReader fr;
    private BufferedReader br;

    public Board() throws IOException{
        readFile();
        board = fillBoard(br);
    }

    public void readFile(){
        try{
            fich = new File("C:/Users/Julio/Desktop/nivel.txt"); // PENDING CHANGE
            fr = new FileReader (fich);
            br = new BufferedReader(fr);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public char[][] fillBoard( BufferedReader br)throws IOException{
            char item;
            String levelName = br.readLine();
            System.out.println(levelName);
            int j = 0;
            int i = 0;
            //Dimensiones del tablero
            String index= br.readLine();
            nRows = (int)index.charAt(0)-48;
            nColumns = (int)index.charAt(2)-48;
            //Rellenamos el char[][]
            char [][] tablero = new char[nRows][nColumns];
            while((index = br.readLine())!= null && i < nRows){
                System.out.println(index);
                j = 0;
                while(j< nColumns){
                 tablero[i][j]=index.charAt(j);
                 j++;
                }

                i++;
            }
            return tablero;
    }

    public static void main(String[] args)throws IOException  {
        Board a = new Board();

        for (int x=0; x < a.board.length; x++) {
            System.out.print("|");
            for (int y=0; y < a.board[x].length; y++) {
              System.out.print (a.board[x][y]);
              if (y!=a.board[x].length-1) System.out.print("\t");
            }
            System.out.println("|");
          }

    }

    public String toString(){
        return board.toString();
    }
}
