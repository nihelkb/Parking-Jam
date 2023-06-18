package es.upm.pproject.parkingjam.common;

/**
 * Class that represents pairs with left and right values.
 * 
 * @author Nihel Kella Bouziane
 * @version 1.0
 * @since 15/06/2023
 */
public class Pair<X,Y>{
    private X left;
    private Y right;
    
    public Pair(X paramX, Y paramY) {
        this.left = paramX;
        this.right = paramY;
    }
    
    public Pair(Pair<X, Y> paramPair) {
        this.left = paramPair.getLeft();
        this.right = paramPair.getRight();
    }
    
    public X getLeft() {
        return this.left;
    }
    
    public Y getRight() {
        return this.right;
    }
    
    public void setLeft(X paramX) {
        this.left = paramX;
    }
    
    public void setRight(Y paramY) {
        this.right = paramY;
    }

    public String toString() {
        return "Pair(" + this.left + "," + this.right + ")";
      }
}