package es.upm.pproject.parkingjam.view.panels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
* Class that extends a JPanel to represent the parking in the game board.
* The ParkingPanel is responsible for displaying the parking area where the cars are placed.
* @author Nihel Kella Bouziane
* @version 1.0
* @since 18/06/2023
*/
public class ParkingPanel extends JPanel{

    /**
     * Constructs a ParkingPanel object with the specified width and height.
     * @param width  The width of the parking panel.
     * @param height The height of the parking panel.
     */
    public ParkingPanel(int width, int height) {
        Dimension size = new Dimension(width,height);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
        this.setBackground(new Color(105,105,105));
    }
}