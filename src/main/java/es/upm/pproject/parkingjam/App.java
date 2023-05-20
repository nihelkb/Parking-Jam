package es.upm.pproject.parkingjam;

import java.util.Scanner;

import es.upm.pproject.parkingjam.models.*;

public class App {

    public static void main(String[] args) {
        Level level1 = new Level();
        try {
            level1.loadLevel();
            int levelScore = 0;

            /*
            for(int i = 0; i < level1.getVehicles().size(); i++ ){
                System.out.println(level1.getVehicles().get(i));
            }
            */
            for (int x=0; x < level1.getBoard().getBoard().length; x++) {
                System.out.print("|");
                for (int y=0; y < level1.getBoard().getBoard().length; y++) {
                    System.out.print (level1.getBoard().getBoard()[x][y]);
                    if (y!=level1.getBoard().getBoard()[x].length-1) 
                        System.out.print("\t");
                }
            System.out.println("|");
            }

            
           //System.out.println(level1.getVehicles().toString());
            
            Scanner sc = new Scanner(System.in);
            Boolean salida = true;
            while(salida){ 
                
                System.out.println("Choose the vehicle you want to move:"); 
                char nombreVehiculo = ' ';
                
                nombreVehiculo = sc.nextLine().charAt(0);
                
                Vehicle vehicle = null;
                if(level1.getVehicles().containsKey(nombreVehiculo)){
                    vehicle = level1.getVehicles().get(nombreVehiculo);
                }else{
                    sc.close();
                    return;
                }
                
                System.out.println("Choose the direction (up, down, left, right):");
                String direccion = sc.nextLine();
                
                System.out.println("Choose the number of movements:");
                int distancia = sc.nextInt();
                
                boolean movimientoExitoso = level1.getBoard().updateBoard(vehicle, direccion, distancia);
        
                if (!movimientoExitoso) {
                    System.out.println("Invalid movement, try again.");
                    System.out.println("Level score: " + levelScore);
                }else{
                    levelScore++;
                    System.out.println("Level score: " + levelScore);
                }
                
                
                for (int x=0; x < level1.getBoard().getBoard().length; x++) {
                    System.out.print("|");
                    for (int y=0; y < level1.getBoard().getBoard().length; y++) {
                        System.out.print (level1.getBoard().getBoard()[x][y]);
                        if (y!=level1.getBoard().getBoard()[x].length-1) System.out.print("\t");
                    }
                    System.out.println("|");
                }
                
                sc.nextLine();
            }
            sc.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
