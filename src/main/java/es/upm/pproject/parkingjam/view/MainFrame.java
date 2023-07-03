package es.upm.pproject.parkingjam.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import es.upm.pproject.parkingjam.common.Coordinates;
import es.upm.pproject.parkingjam.controller.Controller;
import es.upm.pproject.parkingjam.view.panels.ParkingPanel;
import es.upm.pproject.parkingjam.view.panels.CarPanel;
import es.upm.pproject.parkingjam.view.panels.ImagePanel;
import es.upm.pproject.parkingjam.view.utils.Constants;

public class MainFrame extends JFrame  {

    private Map<Integer,List<String>> carSpritesMap;
    private Map<Character, CarPanel> mapCarPanels;

    private ImagePanel backgroundSprite;
    private JPanel grid;
    private JPanel stats;

    private JLabel levelName;
    private JLabel gameScore;
    private JLabel levelScore;

    private Dimension levelDimension;

    private transient Controller controller;
    
    public MainFrame(Controller control) {
        super("Parking Jam");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mapCarPanels = new HashMap<>();
        this.controller = control;
        this.setResizable(false);
        this.getContentPane().setPreferredSize(
                new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        grid = new JPanel();
        grid.setBounds(Constants.STATS_WIDTH, Constants.STATS_HEIGHT, 
                        Constants.SCREEN_WIDTH - Constants.STATS_WIDTH, 
                       Constants.SCREEN_WIDTH - Constants.STATS_HEIGHT);
        grid.setLayout(null);

        backgroundSprite = new ImagePanel(Constants.BACKGROUND, Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
        backgroundSprite.setLocation(0, Constants.STATS_HEIGHT);

        stats = new JPanel(new GridLayout(1,3));
        stats.setBounds(0,0,Constants.STATS_WIDTH,Constants.STATS_HEIGHT);
        stats.setBackground(Constants.STATS_COLOR);
        // Padding for the text
        stats.setBorder(new EmptyBorder(0, 20, 0, 20));

        levelScore = new JLabel("levelScore", SwingConstants.RIGHT);
        gameScore = new JLabel("gameScore", SwingConstants.LEFT);
        levelName = new JLabel("levelName", SwingConstants.CENTER);
        levelScore.setLocation(0, 25);
        gameScore.setLocation(0, 25);
        levelName.setLocation(0, 25);
        gameScore.setLabelFor(stats);
        levelName.setLabelFor(stats);
        levelScore.setLabelFor(stats);

        this.getContentPane().add(stats);
        this.getContentPane().add(grid);
        this.setAlwaysOnTop(false);

        this.setIconImage(new ImageIcon(Constants.ICON).getImage());

        buildMenuBar();
    }

    public void init() {
        showLevel();
        this.setVisible(true);
    }

    // Repinta despues de cada nivel completado
    public void showLevel() {
        mapCarPanels.clear();
        levelDimension = controller.getLevelDimension();
        carSpritesMap = createSpriteMap();
        repaintLevel();
        this.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2 - 35);
    }

    public void repaintLevel() {
        grid.removeAll();
        repaintStats();
        paintParking();
        repaintParking();
        repaintBackground();
        grid.repaint();
        this.pack();
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

    public void repaintStats(){
        stats.removeAll();
        levelName.setText(controller.getLevelName());
        levelName.setFont(Constants.LEVEL_FONT);
        gameScore.setText("Score: " + controller.getGameScore());
        levelScore.setText("Level score: " + controller.getLevelScore());
        stats.add(gameScore);
        stats.add(levelName);
        stats.add(levelScore);
        stats.repaint();
    }
    
    private void repaintBackground(){
        grid.add(backgroundSprite);
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
            mapCarPanels.put(idCar,image);
            grid.add(image);
            
        }
        
        char[][] board = controller.getLevelBoard();
        // Dibujar los muros en la interfaz
        int zero = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[zero].length; j++) {
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

    public void undoRedo(char id){
        CarPanel a = mapCarPanels.get(id);
        Coordinates carCoordinates = controller.getCarPosition(id);
        a.setLocation(a.getInitialX() + carCoordinates.getY()*Constants.TILE_SIZE,a.getInitialY() + carCoordinates.getX()*Constants.TILE_SIZE);
    }

    private void buildMenuBar() {
        JMenuBar menuBarComp = new JMenuBar();
        JFrame main = this;

        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGame = new JMenuItem("New game");
        ActionListener actionListener = e -> 
            controller.newGame();
    
        newGame.addActionListener(actionListener);
        gameMenu.add(newGame);

        JMenuItem resetLevel = new JMenuItem("Reset level");
        ActionListener actionResetLevel = e ->
            controller.resetLevel();

        resetLevel.addActionListener(actionResetLevel);   
        gameMenu.add(resetLevel);

        JMenuItem loadGame = new JMenuItem("Load...");
        ActionListener actionLoadGame= e -> controller.loadGame();        
        loadGame.addActionListener(actionLoadGame);
        gameMenu.add(loadGame);

        JMenuItem saveGame = new JMenuItem("Save");
        ActionListener actionSaveGame = e -> 
            controller.saveGame();
        
        saveGame.addActionListener(actionSaveGame);
        gameMenu.add(saveGame);

        JMenuItem exitGame = new JMenuItem("Exit");
        exitGame.addActionListener(e -> main.dispose());
        gameMenu.addSeparator();
        gameMenu.add(exitGame);

        menuBarComp.add(gameMenu);

        JMenu undo = new JMenu("Undo");
        JMenu redo = new JMenu("Redo");

        // Crear una clase anónima que implemente MouseListener
        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == undo) {
                    controller.undo();
                } else if (e.getSource() == redo) {
                    controller.redo();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // No es necesario implementar este método
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // No es necesario implementar este método
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // No es necesario implementar este método
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // No es necesario implementar este método
            }
        };

        // Asignar el mismo listener a ambos componentes
        undo.addMouseListener(mouseListener);
        redo.addMouseListener(mouseListener);

        menuBarComp.add(undo);
        menuBarComp.add(redo);

        JMenu soundMenu = new JMenu("Sound");
        JCheckBoxMenuItem mute = new JCheckBoxMenuItem("Mute", false);

        soundMenu.add(mute);
        menuBarComp.add(soundMenu);

        JMenu help = new JMenu("Help");
        menuBarComp.add(help);

        this.setJMenuBar(menuBarComp);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            // Not able
        }
    }

}