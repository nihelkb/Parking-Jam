package es.upm.pproject.parkingjam.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import es.upm.pproject.parkingjam.common.Coordinates;
import es.upm.pproject.parkingjam.interfaces.IController;
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

    private boolean isMuted = false;

    private transient IController controller;

    private boolean firstTime = true;

    private static final Logger logger = LoggerFactory.getLogger(MainFrame.class);
    private static final Marker guiMarker = MarkerFactory.getMarker("GUI");
    private static final Marker fatalMarker = MarkerFactory.getMarker("FATAL");
    /**
     * Constructor of the class.
     * @param controller the controller that controls the game.
     */
    public MainFrame(IController controller) {
        super("Parking Jam");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mapCarPanels = new HashMap<>();
        this.controller = controller;
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

        this.setIconImage(new ImageIcon(Constants.APP_ICON).getImage());

        buildMenuBar();

        controller.startBackgroundMusic();
    }

     /**
     * Method that starts the graphical interface showing the level. 
     */
    public void init() {
        showLevel();
        if(firstTime){ // to avoid moving the frame position 
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2 - 35);
            firstTime = false;
        }
        this.setVisible(true);
    }

     /**
     * Method that repaints after the level is completed. 
     */
    public void showLevel() {
        mapCarPanels.clear();
        levelDimension = controller.getLevelDimension();
        carSpritesMap = createSpriteMap();
        repaintLevel();
        stats.validate();
    }


    /**
     * Method that repaints the level. 
     */
    public void repaintLevel() {
        grid.removeAll();
        repaintStats();
        paintParking();
        repaintParking();
        repaintBackground();
        grid.repaint();
        this.pack();
    }
    
    /**
     * Method that repaints the parking of each level. 
     */
    private void repaintParking() {
        int width = (int)levelDimension.getWidth()*Constants.TILE_SIZE;
        int heigth = (int)levelDimension.getHeight()*Constants.TILE_SIZE;
        int initialX = (Constants.SCREEN_WIDTH/2 - width/2);
        int initialY = (Constants.SCREEN_HEIGHT/2 - heigth/2);
        ParkingPanel parking = new ParkingPanel(width,heigth);
        parking.setLocation(initialX, initialY + Constants.STATS_HEIGHT/2);
        grid.add(parking);
    }

    /**
     * Method that repaints the stats of each level. 
     */
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
    
    /**
     * Method that repaints the background. 
     */
    private void repaintBackground(){
        grid.add(backgroundSprite);
    }

    /**
     * Method that paints all the cars and trunks. 
     */
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
    /**
     * Method that creates the sprites of the cars that fit the most.
     * @return the map with the sprites.
     */
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

    /**
     * Method that shows the congrats message when you win the game
     * @param gameScore the score of the game.
     */
    public void showCongratsMsg(int gameScore) {
        String message = String.format(
                "<html><div style='text-align: center;'>Congratulations!<br><br>You have completed the game.<br><br>Score: %d</div></html>",
                gameScore);
        JOptionPane.showMessageDialog(null, message, "Game completed", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Constants.CONGRATS_ICON));
    }

     /**
     * Method that undo or redo a movement moving a car. 
     * @param id the car id.
     */
    public void undoRedo(char id){
        CarPanel a = mapCarPanels.get(id);
        Coordinates carCoordinates = controller.getCarPosition(id);
        // move the car
        a.setLocation(a.getInitialX() + carCoordinates.getY()*Constants.TILE_SIZE,a.getInitialY() + carCoordinates.getX()*Constants.TILE_SIZE);
    }

    /**
     * Method that creates the menu. 
     */
    private void buildMenuBar() {
        JMenuBar menuBarComp = new JMenuBar();

        JMenu gameMenu = createGameMenu();
        UndoMenuOptions options = createUndoMenu();
        JMenu undo = options.getUndoMenu();
        JMenu redo = options.getRedoMenu();
        JMenu soundMenu = createSoundMenu();
        JMenu help = createHelpMenu();

        menuBarComp.add(gameMenu);
        menuBarComp.add(undo);
        menuBarComp.add(redo);
        menuBarComp.add(soundMenu);
        menuBarComp.add(help);

        this.setJMenuBar(menuBarComp);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            // Not able
        }
    }

    /**
     * Method that creates all the buttons and actionlisteners of the menu.
     * @return the game menu component. 
     */
    private JMenu createGameMenu(){
        JMenu gameMenu = new JMenu("Game");

        JMenuItem newGame = new JMenuItem("New game");
        newGame.addActionListener(e -> handleNewGameAction());

        JMenuItem resetLevel = new JMenuItem("Reset level");
        resetLevel.addActionListener(e -> handleResetLevelAction());

        JMenuItem loadGame = new JMenuItem("Load...");
        loadGame.addActionListener(e -> handleLoadGameAction());

        JMenuItem saveGame = new JMenuItem("Save");
        saveGame.addActionListener(e -> handleSaveGameAction());

        JMenuItem exitGame = new JMenuItem("Exit");
        exitGame.addActionListener(e -> System.exit(0));

        gameMenu.add(newGame);
        gameMenu.add(resetLevel);
        gameMenu.add(loadGame);
        gameMenu.add(saveGame);
        gameMenu.addSeparator();
        gameMenu.add(exitGame);

        return gameMenu;
    }


    /**
     * Method that creates the sound menu. 
     * @return the sound menu component.
     */
    private JMenu createSoundMenu() {
        JMenu soundMenu = new JMenu("Sound");
        JCheckBoxMenuItem mute = new JCheckBoxMenuItem("Mute", false);

        mute.addActionListener(e -> handleMuteAction(mute.isSelected()));

        soundMenu.add(mute);

        return soundMenu;
    }

    /**
     * Method that creates the undo and redo menu. 
     * @return the undoMenuOptions with the undo and redo menu components.
     */
    private UndoMenuOptions createUndoMenu() {
        JMenu undo = new JMenu("Undo");
        JMenu redo = new JMenu("Redo");

        MouseListener mouseListener = createUndoRedoMouseListener(undo, redo);
        undo.addMouseListener(mouseListener);
        redo.addMouseListener(mouseListener);

        return new UndoMenuOptions(undo, redo);
    }
    
    /**
     * Method that undo or redo a movement moving a car. 
     * @param undo the JMenu of the undo movement.
     * @param redo the JMenu of the redo movement.
     * @return the mouseListener associated to undo and redo menus.
     */
    private MouseListener createUndoRedoMouseListener(JMenu undo, JMenu redo) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == undo) {
                    controller.undo();
                } else if (e.getSource() == redo) {
                    controller.redo();
                }
                if(!isMuted)
                    controller.playUndoSound();
            }
        };
    }

    /**
     * Method that creates the help menu. 
     * @return the help menu component.
     */
    private JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu("Help");
        helpMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isMuted) {
                    controller.playDefaultSound();
                }
                logger.info(guiMarker, Constants.HELP_MSG_LOGGER);
                JOptionPane.showMessageDialog(null, Constants.HELP, "How to play", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        return helpMenu;
    }

    /**
     * Method that creates a new game and mute the music or no. 
     */
    private void handleNewGameAction() {
        controller.newGame();
        if (isMuted) {
            pauseBackgroundMusic();
        } else {
            controller.playNewGameSound();
            controller.restartBackgroundMusic();
        }
    }

    /**
     * Method that resets a game and mute the music or no. 
     */
    private void handleResetLevelAction() {
        controller.resetLevel();
        if (isMuted) {
            pauseBackgroundMusic();
        } else {
            controller.restartBackgroundMusic();
            controller.playResetSound();
        }
    }

     /**
     * Method that load a game and mute the music or no. 
     */
    private void handleLoadGameAction(){
        if (!isMuted) {
            controller.playDefaultSound();
        }
        controller.loadGame();
    }

    /**
     * Method that pauses the music. 
     */
    private void pauseBackgroundMusic() {
        controller.pauseBackgroundMusic();
    }

     /**
     * Method that resumes the music. 
     */
    private void resumeBackgroundMusic() {
        controller.resumeBackgroundMusic();
    }


    /**
     * Method that saves a game and mute the music or no. 
     */
    private void handleSaveGameAction(){
        if (!isMuted) {
            controller.playDefaultSound();
        }
        controller.saveGame();
    }

    /**
     * Method that handles the mute music action. 
     * @param isMuted true if the game has been muted, false if unmuted
     */
    private void handleMuteAction(boolean isMuted) {
        this.isMuted = isMuted;
        if (isMuted){
            logger.info(guiMarker, "Game is muted");
            pauseBackgroundMusic();
        } else {
            logger.info(guiMarker, "Game is unmuted");
            controller.playDefaultSound();
            resumeBackgroundMusic();
        }
    } 

    /**
    * Method that returns if the music is muted.
    * @return true if the music is muted, false otherwise. 
    */
    public boolean isGameMuted(){
        return this.isMuted;
    }

    /**
    * Class that controls the JMenu of the undo and redo
    */
    public class UndoMenuOptions {
        private JMenu undo;
        private JMenu redo;

        public UndoMenuOptions(JMenu undo, JMenu redo) {
            this.undo = undo;
            this.redo = redo;
        }

        public JMenu getUndoMenu() {
            return undo;
        }

        public JMenu getRedoMenu() {
            return redo;
        }
    }

    /**
    * Method that creates a generic fileChooser that filters by txt files.
    * @return the created fileChooser. 
    */
    private JFileChooser getFileChooser(){
        // Crear un objeto JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        
        // Establecer el filtro de extensión del archivo (opcional)
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt");
        fileChooser.setFileFilter(filter);

        return fileChooser;
    }

    /**
    * Method that displays an open fileChooser and gets the path of the file.
    * @return the path of the selected file. 
    */
    public String openFileChooser(){
        JFileChooser fileChooser = getFileChooser();
        int result = fileChooser.showOpenDialog(null);
        java.io.File selectedFile = null; 
        if (result == JFileChooser.APPROVE_OPTION) {
            // Obtener el archivo seleccionado
            selectedFile = fileChooser.getSelectedFile();
            boolean isTxt = selectedFile.getName().endsWith(".txt");
            if(!isTxt){
                JOptionPane.showMessageDialog(null, Constants.WRONG_FILE, "Error while loading game", JOptionPane.ERROR_MESSAGE);
                logger.error(fatalMarker, Constants.WRONG_FILE);
                return null;
            }
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

    /**
    * Method that displays a save fileChooser and gets the path of the file.
    * @return the path of the selected file. 
    */
    public String saveFileChooser(){
        JFileChooser fileChooser = getFileChooser();
        int result = fileChooser.showSaveDialog(null);
        java.io.File selectedFile = null; 
        if (result == JFileChooser.APPROVE_OPTION) {
            // Obtener el archivo seleccionado
            selectedFile = fileChooser.getSelectedFile();
            String[] fileName = selectedFile.getName().split("\\.");
            if(fileName.length == 1){ // Add .txt
                String nameWithTxt = selectedFile.getName() + ".txt";
                File fileWithTxt = new File(selectedFile.getParent(), nameWithTxt);
                try {
                    if(!fileWithTxt.createNewFile()){
                        JOptionPane.showMessageDialog(null, Constants.WRONG_FILE, Constants.ERROR_SAVING, JOptionPane.ERROR_MESSAGE);
                        logger.error(fatalMarker, Constants.WRONG_FILE);
                        return null; 
                    }else{
                        selectedFile = fileWithTxt;
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, Constants.WRONG_FILE, Constants.ERROR_SAVING, JOptionPane.ERROR_MESSAGE);
                    logger.error(fatalMarker, Constants.WRONG_FILE);
                    return null;
                }
                
            }else if(selectedFile.getName().endsWith(".txt")){ // correct format
            }else{ // wrong format
                JOptionPane.showMessageDialog(null, Constants.WRONG_FILE, Constants.ERROR_SAVING, JOptionPane.ERROR_MESSAGE);
                logger.error(fatalMarker, Constants.WRONG_FILE);
                return null;
            }
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

    /**
    * Method that displays an error dialog when trying to save the game when it is completed.
    */
    public void cannotSaveGame(){
        JOptionPane.showMessageDialog(null, "It is not possible to save a game that has been completed.", Constants.ERROR_SAVING, JOptionPane.ERROR_MESSAGE);
        logger.error(fatalMarker, Constants.GAME_FINISHED_SAVE);
    }

}