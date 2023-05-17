package es.upm.pproject.parkingjam.models;
import java.io.*;

public class Board {
<<<<<<< HEAD
    
=======
>>>>>>> e32dfd4914e275675d411e913c603b720bf5e7b5
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
<<<<<<< HEAD
            fich = new File("C:\\Users\\Julio\\Desktop\\nivel.txt");
=======
            fich = new File("C:/Users/Julio/Desktop/nivel.txt"); // PENDING CHANGE
>>>>>>> e32dfd4914e275675d411e913c603b720bf5e7b5
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
<<<<<<< HEAD
=======
                System.out.println(index);
>>>>>>> e32dfd4914e275675d411e913c603b720bf5e7b5
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
<<<<<<< HEAD
    
=======

>>>>>>> e32dfd4914e275675d411e913c603b720bf5e7b5
        for (int x=0; x < a.board.length; x++) {
            System.out.print("|");
            for (int y=0; y < a.board[x].length; y++) {
              System.out.print (a.board[x][y]);
              if (y!=a.board[x].length-1) System.out.print("\t");
            }
            System.out.println("|");
          }
<<<<<<< HEAD
        
=======

>>>>>>> e32dfd4914e275675d411e913c603b720bf5e7b5
    }

    public String toString(){
        return board.toString();
    }
}
