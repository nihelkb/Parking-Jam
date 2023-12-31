package es.upm.pproject.parkingjam.view.panels;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.event.*;

import es.upm.pproject.parkingjam.common.Pair;
import es.upm.pproject.parkingjam.interfaces.IController;
import es.upm.pproject.parkingjam.view.utils.Constants;

/**
* Class that extends a ImagePanel to represent a car on a game board.
* The CarPanel is responsible for displaying a car image and handling 
* the mouse events for dragging and releasing the car.
* @author Nihel Kella Bouziane
* @version 1.0
* @since 18/06/2023
*/
public class CarPanel extends ImagePanel implements MouseMotionListener, MouseListener {

    private char idCar;
    private boolean horizontal;
    private transient IController controller;
    private int initialX;
    private int initialY;
    
    private Point initialClick;
    private Point initialPositionOnScreen;
    private int moved;

    // Auxiliar variable used to only play car movement sound if the car has been moved
    private boolean carMoved = false;
    
     /**
     * Constructs a CarPanel object with the specified car ID, sprite path, dimensions, initial position, and controller.
     * @param idCar       The ID of the car.
     * @param spritePath  The path of the car's sprite image.
     * @param width       The width of the car panel.
     * @param height      The height of the car panel.
     * @param initialX    The initial X position of the car panel.
     * @param initialY    The initial Y position of the car panel.
     * @param controller  The controller instance for handling car movements.
     */
    public CarPanel(char idCar, String spritePath, int width, int height, int initialX, int initialY, IController controller) {
        super(spritePath, width, height);
        this.idCar = idCar;
        this.horizontal = controller.isCarHorizontal(idCar);
        this.controller = controller;
        this.initialX = initialX;
        this.initialY = initialY;
        this.moved = 0;
        if (!horizontal) {
            this.image = rotateImage(image);
        }
        this.setLocation(initialX +  controller.getCarPosition(idCar).getY()*Constants.TILE_SIZE,initialY +  controller.getCarPosition(idCar).getX()*Constants.TILE_SIZE);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }

    /**
     * Rotates the given image by 90 degrees clockwise.
     * @param image The image to rotate.
     * @return The rotated image.
     */
    private Image rotateImage(Image image) {
        // Calculate the dimensions of the rotated image
        double radians = Math.toRadians(90);
        int width = (int) Math.ceil(image.getWidth(null) * Math.abs(Math.cos(radians))
                + image.getHeight(null) * Math.abs(Math.sin(radians)));
        int height = (int) Math.ceil(image.getWidth(null) * Math.abs(Math.sin(radians)) + image.getHeight(null) * Math.abs(Math.cos(radians)));

        // Create an empty image with the new dimensions
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Get the graphics context of the image
        Graphics2D g2d = bufferedImage.createGraphics();

        // Move the origin of coordinates to the center of the image
        g2d.translate(width / 2, height / 2);

        // Apply the rotation transformation
        AffineTransform transform = new AffineTransform();
        transform.rotate(radians);
        g2d.transform(transform);

        // Draw the rotated image centered on the new canvas
        g2d.drawImage(image, -image.getWidth(null) / 2, -image.getHeight(null) / 2, null);
        g2d.dispose();

        return bufferedImage;
    }

    /**
     * Calculates the movement of the car based on the final position on the screen.
     * @param finalPositionOnScreen The final position of the car on the screen.
     * @return A Pair containing the direction ('R', 'L', 'D', 'U') and the number of sprites moved.
     */
    private Pair<Character, Integer> calculateMovement(Point finalPositionOnScreen) {
        int positionDiff = 0;
        double spritesMoved = 0;
        int spritesMovedInt = 0;
        int sign = 1;
        char dir = 0;
        if (horizontal) {
            positionDiff = (int)(finalPositionOnScreen.getX() - this.initialPositionOnScreen.getX());
            sign = positionDiff > 0 ? 1 : -1;
            dir = sign == 1 ? 'R' : 'L';
        }
        else {
            positionDiff = (int)(finalPositionOnScreen.getY() - this.initialPositionOnScreen.getY());
            sign = positionDiff > 0 ? 1 : -1;
            dir = sign == 1 ? 'D' : 'U';
        }
        spritesMoved = (double)positionDiff / Constants.TILE_SIZE;
        spritesMovedInt = (Math.abs(spritesMoved) % 1) > 0 ? (int)spritesMoved + sign*1 : (int)spritesMoved;
        return new Pair<>(dir, Math.abs(spritesMovedInt));
    }

    @Override
    public void mouseDragged(MouseEvent e) { // Arrastrar
        int x = this.getLocation().x; // Car position in frame
        int y = this.getLocation().y;
        if (horizontal) {
            // Determine how much the mouse moved since the initial click
            int xMoved = (x + e.getX()) - (x + initialClick.x);
            x = x + xMoved;
        }
        else {
            int yMoved = (y + e.getY()) - (y + initialClick.y);
            y = y + yMoved;
        }
        // Check if the movement is valid
        Pair<Character,Integer> movement = calculateMovement(e.getLocationOnScreen());
        boolean canMove = controller.canMove(idCar, movement.getLeft(), movement.getRight());
        if (canMove) {
            // Save valid movements
            this.moved = movement.getRight();
            // Update position in frame
            this.setLocation(x, y);
            carMoved = true;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Method not used
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Method not used
    }

    @Override
    public void mousePressed(MouseEvent e) { 
        this.initialClick = e.getPoint();                       // java.awt.Point[x=26,y=59] -> CarPanel coords
        this.initialPositionOnScreen = e.getLocationOnScreen(); // java.awt.Point[x=1343,y=768] -> Screen coor
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Pair<Character,Integer> movement = calculateMovement(e.getLocationOnScreen());
        controller.move(idCar, movement.getLeft(), this.moved);
        this.setLocation(initialX + controller.getCarPosition(idCar).getY()*Constants.TILE_SIZE,initialY + controller.getCarPosition(idCar).getX()*Constants.TILE_SIZE);
        this.moved = 0;
        if(carMoved){
            if(!controller.isGameMuted())
                controller.playMoveCarSound();
            carMoved = false;
        }  
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Method not used
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Method not used
    }

    /**
     * Getter of the ID of the car.
     * @return The ID of the car.
     */
    public char getId() {
        return idCar;
    }

    /**
     * Getter of the initial X position of the car panel.
     * @return The initial X position of the car panel.
     */
    public int getInitialX() {
        return initialX;
    }

    /**
     * Getter of the initial Y position of the car panel.
     * @return The initial Y position of the car panel.
     */
    public int getInitialY() {
        return initialY;
    }

}