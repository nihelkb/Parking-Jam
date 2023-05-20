package es.upm.pproject.parkingjam.models;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedList;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;

public class Board extends JFrame{
    
    private char[][] board;
    private int nRows, nColumns;
    private File fich;
    private FileReader fr;
    private BufferedReader br;
    private Lamina milamina;
    


    public Board(){}

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
            char [][] board = new char[nRows][nColumns];
            while((index = br.readLine())!= null && i < nRows){
                j = 0;
                while(j< nColumns){
                 board[i][j]=index.charAt(j);
                 j++;
                }

                i++;
            }
            return board;
    }

    public void setBoard (char[][] board) {
        this.board = board;
    }

    public char[][] getBoard () {
        return this.board;
    }

    public int getNRows () {
        return nRows;
    }

    public int getnColumns () {
        return nColumns;
    }


    public boolean verifyMovement(Vehicle vehicle, int distance, String direction){
        int row = vehicle.getpositionX();
        int column = vehicle.getpositionY();
        char orientation = vehicle.getOrientation();
        int lengthVehicle = vehicle.getLength();
        int contador = 0;
        if(orientation == 'H'){
            if(direction.equals("left")){
                if((column - distance) <= 0) 
                    return false;
                for (int i = column - 1; i > 0 && contador < distance; i--){
                    contador ++;
                    if(board[row][i] != ' ')
                        return false;
                }
                
            }
        
            else if(direction.equals("right")){
                    if((row + distance + (lengthVehicle -1)) >= nColumns) 
                        return false;
                    for (int i = column + lengthVehicle; i < nColumns -1 && contador < distance; i++){
                        contador ++;
                        if(board[row][i] != ' ')
                            return false;
                        
                    }             
            }
            else{
                return false;
            }
        }
        if(orientation == 'V'){
            if(direction.equals("up")){
                if((row - distance) <= 0) 
                    return false;
                for (int i = row - 1; i > 0  && contador < distance; i--){
                    contador ++;
                    if(board[i][column] != ' ')
                        return false;
                }
                
            }
        
            else if(direction.equals("down")){
                    if((row + distance + lengthVehicle - 1) >= nRows) 
                        return false;
                    
                    for (int i = row + lengthVehicle ; i < nRows -1 && contador < distance; i++){
                        contador++;
                        if(board[i][column] != ' ')
                            return false;      
                    }             
            }

            else{
                return false;
            }
        }
        return true;

    }

    public void deleteCar(Vehicle vehicle){
        char orientation = vehicle.getOrientation();
        int length = vehicle.getLength();
        int posX = vehicle.getpositionX();
        int posY = vehicle.getpositionY();
        if(orientation == 'H'){
            for (int j = posY; j < length + posY; j++){

                board[posX][j] = ' ';
                
            }
        } else if(orientation == 'V'){
            //System.out.println(posY);
            for (int i = posX; i < posX + length; i++){
                //System.out.println(board[i][posY]);
                board[i][posY] = ' ';
            }
        }
    }

    public void insertCar(Vehicle vehicle) {
        int posX = vehicle.getpositionX();
        int posY = vehicle.getpositionY();
        int length = vehicle.getLength();
        Character id = vehicle.getId();
        char orientation = vehicle.getOrientation();
        if (orientation == 'H') {
            for (int j = posY; j < posY + length; j++){
                board[posX][j] = id; 
            }
        }else if (orientation == 'V') {
            for (int i = posX; i < posX + length; i++){
                board[i][posY] = id;
            }
        }
    }

    public boolean updateBoard(Vehicle vehicle, String direction, int distance){
        if(verifyMovement(vehicle, distance, direction)){
            Character id = vehicle.getId();
            deleteCar(vehicle);
            vehicle.move(direction, distance);
            insertCar(vehicle);
            //System.out.println("Movimiento valido");
            return true;
        }
        
        else{
            //System.out.println("Movimiento no válido");
            return false;
        }


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
    
    public void paintBoard(){
        //Crear pantalla
        setTitle("hola");
        setSize(1000, 1000);
        setMaximumSize(new Dimension (300, 400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        //Crear lámina
        milamina = new Lamina();
        add(milamina);
        
        
                //JButton start = new JButton("Start");
        
   
    }

    private void hidePanelElements() {
        Component[] components = milamina.getComponents();
        for (Component component : components) {
            component.setVisible(false);
        }

        milamina.revalidate();
        milamina.repaint();
    }
    class Lamina extends JPanel {
        private Image image;
        private ImageIcon icon;
        private JButton start;
        //Crea pantalla inicio
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            icon = new ImageIcon("Imagenes\\start.png");
            Image botonImage = icon.getImage();
            Image botonScaled = botonImage.getScaledInstance(375, 300, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(botonScaled);
            start = new JButton("start", scaledIcon);
            
            start.setContentAreaFilled(false);
            try {
                image = ImageIO.read(new File("Imagenes\\intro.jpg"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("Imagen no encontrada");
            }
            //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            // g.fillRect(0, 0, 1000, 1000);
            g.drawImage(image, 0, 0 , 1000, 1000, null);
            start.setBounds(300, 780, 350, 150); // 
            add(start);

            start.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    hidePanelElements();
                    
                }

            });
            getContentPane().setLayout(new BorderLayout());
            getContentPane().add(start, BorderLayout.NORTH);
            getContentPane().add(milamina, BorderLayout.CENTER);

        }
        
      



        
    }

    public String toString(){
        return board.toString();
    }
}
