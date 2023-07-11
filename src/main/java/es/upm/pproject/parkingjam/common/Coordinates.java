package es.upm.pproject.parkingjam.common;

/**
 * Class that represents a pair of coordinates x and y.
 * 
 * @author Nihel Kella Bouziane
 * @version 1.0
 * @since 15/06/2023
 */
public class Coordinates{
    private int x;
    private int y;
    
    /**
    * Constructor of the class.
    * @param x x coordinate
    * @param y y coordinate
    */
    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Getter of the x coordinate
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Getter of the y coordinate
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Coordinates other = (Coordinates) obj;
        return this.x == other.x && this.y == other.y;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return String.format("[%d,%d]",x,y);
    }
}