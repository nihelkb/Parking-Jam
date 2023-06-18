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
    
    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * returns the x coordinate
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * returns the y coordinate
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj.getClass() == Coordinates.class) &&
                this.x == ((Coordinates)obj).x && this.y == ((Coordinates)obj).y;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)",x,y);
    }
}