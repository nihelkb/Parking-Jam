package es.upm.pproject.parkingjam.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import es.upm.pproject.parkingjam.controller.Controller;
import es.upm.pproject.parkingjam.view.panels.ParkingPanel;
import es.upm.pproject.parkingjam.view.panels.CarPanel;
import es.upm.pproject.parkingjam.view.panels.ImagePanel;
import es.upm.pproject.parkingjam.view.utils.Constants;

public class MainFrame extends JFrame{

    private static final ImagePanel BACKGROUND_SPRITE = new ImagePanel(
        Constants.BACKGROUND, Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);

    private Map<Integer,List<String>> carSpritesMap;

    private JPanel grid;

    private Dimension levelDimension;

    private transient Controller controller;
    
    public MainFrame(Controller control) {
        super("Parking Jam");
        this.controller = control;
        this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2 - 35);
        this.getContentPane().setPreferredSize(
                new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        grid = new JPanel();
        grid.setBounds(Constants.STATS_WIDTH, Constants.STATS_HEIGHT, 
                        Constants.SCREEN_WIDTH - Constants.STATS_WIDTH, 
                       Constants.SCREEN_WIDTH - Constants.STATS_HEIGHT);
        grid.setLayout(null);

        BACKGROUND_SPRITE.setLocation(0, Constants.STATS_HEIGHT);

        this.getContentPane().add(grid);
        this.setIconImage(new ImageIcon(Constants.BACKGROUND).getImage());
    }

    public void init() {
        showLevel();
        this.setVisible(true);
    }

    // Repinta despues de cada nivel completado
    public void showLevel() {
        levelDimension = controller.getLevelDimension();
        carSpritesMap = createSpriteMap();
        repaintLevel();
    }

    private void repaintParking() {
        int width = (int)levelDimension.getWidth()*Constants.TILE_SIZE;
        int heigth = (int)levelDimension.getHeight()*Constants.TILE_SIZE;
        int initialX = (Constants.SCREEN_WIDTH/2 - width/2);
        int initialY = (Constants.SCREEN_HEIGHT/2 - heigth/2);
        ParkingPanel parking = new ParkingPanel(width,heigth);
        parking.setLocation(initialX, initialY + Constants.STATS_HEIGHT/2);
        grid.add(parking);
    }
    
    public void repaintLevel() {
        grid.removeAll();
        paintParking();
        repaintParking();
        repaintBackground();
        grid.repaint();
        this.pack();
    }

    private void repaintBackground(){
        grid.add(BACKGROUND_SPRITE);
    }

    // Coches y troncos
    public void paintParking() {
        int widthLvl = (int)levelDimension.getWidth()* Constants.TILE_SIZE;
        int heigthLvl = (int)levelDimension.getHeight()*Constants.TILE_SIZE;
        int initialX = (Constants.SCREEN_WIDTH - widthLvl)/2;
        int initialY = (Constants.SCREEN_HEIGHT - heigthLvl)/2 + Constants.STATS_HEIGHT/2;
        List<Character> cars = controller.getCarIds();
        for (Character idCar : cars) {
            char orientation = controller.isCarHorizontal(idCar) ? 'H' : 'V';
            int length = controller.getCarLength(idCar);
            int width = orientation == 'H' ? Constants.TILE_SIZE*length : Constants.TILE_SIZE;
            int height = orientation == 'V' ?  Constants.TILE_SIZE*length : Constants.TILE_SIZE;
            // Ruta de la imagen del coche
            String spritePath = controller.isRedCar(idCar) ? Constants.RED_CAR : Constants.CARS_PATH + carSpritesMap.get(length).remove(0);
            CarPanel image = new CarPanel(idCar, spritePath, width, height, initialX, initialY, controller);
            grid.add(image);
        }
        
        char[][] board = controller.getLevelBoard();
        // Dibujar los muros en la interfaz
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '+') {
                    ImagePanel wall = new ImagePanel(Constants.WALL);
                    // x + j filas, y + i
                    wall.setLocation(initialX + j*Constants.TILE_SIZE, initialY + i*Constants.TILE_SIZE);
                    grid.add(wall);
                }
            }
        }
    }

    private Map<Integer,List<String>> createSpriteMap() {
        // Incluir coches segun tamaño
        List<String> doubles = new LinkedList<>(Arrays.asList("car1.png","car2.png","car3.png","car4.png","car5.png","car6.png","car11.png"));
        List<String> triples = new LinkedList<>(Arrays.asList("car7.png","car8.png","car9.png","car10.png"));
        // Mezclar para tener aleatoriedad
        Collections.shuffle(doubles);
        Collections.shuffle(triples);
        // Crea un mapa con key 2 y valor doubles ...
        return Map.of(2, doubles,3, triples);
    }
}
