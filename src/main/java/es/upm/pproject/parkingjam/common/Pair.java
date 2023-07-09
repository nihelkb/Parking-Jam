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
    
    /**
    * Constructor of the class.
    * @param paramX Left element
    * @param paramY Right element
    */
    public Pair(X paramX, Y paramY) {
        this.left = paramX;
        this.right = paramY;
    }
    
    /**
    * Constructor of the class.
    * @param paramPair Pair to clone
    */
    public Pair(Pair<X, Y> paramPair) {
        this.left = paramPair.getLeft();
        this.right = paramPair.getRight();
    }
    
    /**
     * Getter of the left element
     * @return the left element
     */
    public X getLeft() {
        return this.left;
    }
    
    /**
     * Getter of the right element
     * @return the right element
     */
    public Y getRight() {
        return this.right;
    }
    
    /**
     * Setter of the left element
     * @param paramX the new left element
     */
    public void setLeft(X paramX) {
        this.left = paramX;
    }
    
    /**
     * Setter of the right element
     * @param paramY the new right element
     */
    public void setRight(Y paramY) {
        this.right = paramY;
    }

    @Override
    public String toString() {
        return "Pair(" + this.left + "," + this.right + ")";
      }
}