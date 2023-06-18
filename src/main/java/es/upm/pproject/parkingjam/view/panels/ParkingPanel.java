package es.upm.pproject.parkingjam.view.panels;


import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class ParkingPanel extends JPanel{

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