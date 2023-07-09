package es.upm.pproject.parkingjam.exceptions;

/**
 * Exception that is thrown when the level format is wrong.
 * @author Nihel Kella Bouziane
 * @since 15/06/2023
 * @version 1.0
 */
public class WrongLevelFormatException extends Exception {

    /**
     * Constructs a new WrongLevelFormatException with the specified detail message.
     * @param message the detail message.
    */
    public WrongLevelFormatException(String message) {
        super(message);
    }
}