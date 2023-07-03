package es.upm.pproject.parkingjam.level;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import es.upm.pproject.parkingjam.exceptions.LevelNotFoundException;
import es.upm.pproject.parkingjam.exceptions.WrongLevelFormatException;
import es.upm.pproject.parkingjam.models.Level;

@DisplayName("Class to test the level")
class LevelTest {
    Level level;
    final String usableLevels = "src/main/resources/levels";
    final String testLevels = "src/main/resources/levels4test";
    char board[][];

    @Test
    @DisplayName("Reading level 2 file")
    void test1() throws LevelNotFoundException, WrongLevelFormatException{
        board = new char[8][8];
        board[0][0] = '+';
        board[0][1] = '+';
        board[0][2] = '+';
        board[0][3] = '+';
        board[0][4] = '+';
        board[0][5] = '+';
        board[0][6] = '+';
        board[0][7] = '+';
        board[1][0] = '+';
        board[1][1] = 'a';
        board[1][2] = ' ';
        board[1][3] = ' ';
        board[1][4] = 'b';
        board[1][5] = 'b';
        board[1][6] = 'b';
        board[1][7] = '+';
        board[2][0] = '+';
        board[2][1] = 'a';
        board[2][2] = ' ';
        board[2][3] = ' ';
        board[2][4] = 'c';
        board[2][5] = ' ';
        board[2][6] = 'd';
        board[2][7] = '+';
        board[3][0] = '+';
        board[3][1] = '*';
        board[3][2] = '*';
        board[3][3] = ' ';
        board[3][4] = 'c';
        board[3][5] = 'e';
        board[3][6] = 'd';
        board[3][7] = '@';
        board[4][0] = '+';
        board[4][1] = 'f';
        board[4][2] = 'f';
        board[4][3] = 'f';
        board[4][4] = ' ';
        board[4][5] = 'e';
        board[4][6] = 'd';
        board[4][7] = '+';
        board[5][0] = '+';
        board[5][1] = ' ';
        board[5][2] = ' ';
        board[5][3] = 'g';
        board[5][4] = ' ';
        board[5][5] = 'j';
        board[5][6] = 'j';
        board[5][7] = '+';
        board[6][0] = '+';
        board[6][1] = 'h';
        board[6][2] = 'h';
        board[6][3] = 'g';
        board[6][4] = 'i';
        board[6][5] = 'i';
        board[6][6] = ' ';
        board[6][7] = '+';
        board[7][0] = '+';
        board[7][1] = '+';
        board[7][2] = '+';
        board[7][3] = '+';
        board[7][4] = '+';
        board[7][5] = '+';
        board[7][6] = '+';
        board[7][7] = '+';

        level = new Level(usableLevels + "/level_2.txt");

        assertArrayEquals(level.getBoard().getTiles(), board);
    }
     
    @Test
    @DisplayName("Reading level file - wrong format (invalid path)")
    void test2() throws LevelNotFoundException, WrongLevelFormatException {
        String expectedMessage = "The level src/main/resources/levels/level.txt does not exist";
        Exception exception = assertThrows(LevelNotFoundException.class, () -> {
            level = new Level(usableLevels + "/level.txt");
        });

        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @ParameterizedTest(name = "Reading level file - wrong format: {1}")
    @CsvSource({
            "The level must have one exit,/levelTest3.txt",
            "The level must have one exit,/levelTest4.txt",
            "The level must have one red car,/levelTest5.txt",
            "The level must have one red car,/levelTest6.txt",
            "The level must have one red car,/levelTest7.txt"
    })
    void testWrongLevelFormat(String expectedMessage, String levelFile) throws WrongLevelFormatException {

        Exception exception = assertThrows(WrongLevelFormatException.class, () -> {
            level = new Level(testLevels + levelFile);
        });

        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }
}
