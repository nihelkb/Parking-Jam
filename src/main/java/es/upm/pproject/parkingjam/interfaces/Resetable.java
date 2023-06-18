package es.upm.pproject.parkingjam.interfaces;

/**
 * Interface that enables entities to be restored to their default state.
 * @author Nihel Kella Bouziane
 * @version 1.0
 * @since 15/06/2023
 */
public interface Resetable {
    
    /**
     * Resets to default values
     */
    public void reset();
}