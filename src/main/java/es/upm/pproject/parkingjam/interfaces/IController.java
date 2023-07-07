package es.upm.pproject.parkingjam.interfaces;

import java.awt.Dimension;
import java.util.List;

import es.upm.pproject.parkingjam.common.Coordinates;

/**
 * Interface that defines the controller methods.
 * 
 * @author Nihel Kella Bouziane
 * @version 1.0
 * @since 05/07/2023
 */
public interface IController {

    Dimension getLevelDimension();

    char[][] getLevelBoard();

    boolean canMove(char idCar, char dir, int distance);

    void move(char idCar, char dir, int distance);

    Coordinates getCarPosition(char idCar);

    List<Character> getCarIds();

    boolean isCarHorizontal(char idCar);

    int getCarLength(char idCar);

    boolean isRedCar(char idCar);

    String getLevelName();

    int getGameScore();

    int getLevelScore();

    void undo();

    void redo();

    void newGame();

    void loadGame() ;

    void resetLevel();

    void saveGame();

    void startBackgroundMusic();

    void pauseBackgroundMusic();

    void resumeBackgroundMusic();

    void restartBackgroundMusic();

    void playMoveCarSound();

    void playNewGameSound();

    void playResetSound();

    void playUndoSound();

    void playDefaultSound();

    void playLevelSound();

    void playWinSound();

    boolean isGameMuted();

}