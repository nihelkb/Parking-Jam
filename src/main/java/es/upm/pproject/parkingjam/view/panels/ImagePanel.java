package es.upm.pproject.parkingjam.view.panels;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import es.upm.pproject.parkingjam.view.utils.Constants;

import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics;

/**
* Class that extends a JPanel to represent a tile on a game board.
* @author Nihel Kella Bouziane
* @version 1.0
* @since 18/06/2023
*/
public class ImagePanel extends JPanel{
    protected transient Image image;

    /**
     * Constructs an ImagePanel object with the specified tile image path.
     * @param tilePath Path of the image file to be displayed as a tile.
     */
    public ImagePanel(String tilePath) {
        this(tilePath, Constants.TILE_SIZE, Constants.TILE_SIZE);
    }

    /**
     * Constructs an ImagePanel object with the specified tile image and dimensions.
     * @param tilePath Path of the image file to be displayed as a tile
     * @param width Width of the tile
     * @param height Height of the tile
     */
    public ImagePanel(String tilePath, int width, int height) {
        image = new ImageIcon(tilePath).getImage();
        Dimension size = new Dimension(width,height);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
}