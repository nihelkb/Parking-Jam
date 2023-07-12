package es.upm.pproject.parkingjam.models;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;


import es.upm.pproject.parkingjam.exceptions.LevelNotFoundException;
import es.upm.pproject.parkingjam.exceptions.WrongLevelFormatException;


@DisplayName("Class to test level class")
class LevelTest {
    Level level;
    final String usableLevels = "src/main/resources/levels";
    final String testLevels = "src/main/resources/levels4LevelTest";
    char board[][];

    @Test
    @DisplayName("Reading level 2 file")
    void test1() throws LevelNotFoundException, WrongLevelFormatException{
        char board[][] = {
            {'+', '+', '+', '+', '+', '+', '+', '+'},
            {'+', 'a', ' ', ' ', 'b', 'b', 'b', '+'},
            {'+', 'a', ' ', ' ', 'c', ' ', 'd', '+'},
            {'+', '*', '*', ' ', 'c', 'e', 'd', '@'},
            {'+', 'f', 'f', 'f', ' ', 'e', 'd', '+'},
            {'+', ' ', ' ', 'g', ' ', 'j', 'j', '+'},
            {'+', 'h', 'h', 'g', 'i', 'i', ' ', '+'},
            {'+', '+', '+', '+', '+', '+', '+', '+'}
        };

        this.board = board;

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
    @DisplayName("Test on WrongLevelFormatException")
    @CsvSource({
            "The level must have 7 columns each line,/levelTest1.txt",
            "A level must be surrounded by walls: This level must have 27 walls,/levelTest2.txt",
            "The level must have one exit,/levelTest3.txt",
            "The level must have one exit,/levelTest4.txt",
            "The level must have one red car,/levelTest5.txt",
            "The level must have one red car,/levelTest6.txt",
            "The level must have one red car,/levelTest7.txt",
            "You have to put first the number of rows and then the number of columns,/levelTest8.txt"
            
    })
    void test3(String expectedMessage, String levelFile) throws WrongLevelFormatException {

        Exception exception = assertThrows(WrongLevelFormatException.class, () -> {
            level = new Level(testLevels + levelFile);
        });

        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    private static Stream<Arguments> dataProvider() {
        return Stream.of(
            Arguments.of('d', 'U'),
            Arguments.of('c', 'D'),
            Arguments.of('f', 'R'),
        Arguments.of('f', 'L')
        );
    }

    @ParameterizedTest
    @DisplayName("Test on different moves")
    @MethodSource("dataProvider")
    void test4(char car, char direction) throws LevelNotFoundException, WrongLevelFormatException {
        level = new Level(usableLevels + "/level_1.txt");
        boolean success = level.moveCar(level.getVehiclesMap().get(car), direction, 1, false, false);
        assertTrue(success);
    }

    @Test
    @DisplayName("End of level")
    void test5() throws LevelNotFoundException, WrongLevelFormatException {
        level = new Level(usableLevels + "/level_1.txt");
        level.moveCar(level.getVehiclesMap().get('c'), 'D', 1, false, false);
        level.moveCar(level.getVehiclesMap().get('b'), 'R', 1, false, false);
        level.moveCar(level.getVehiclesMap().get('a'), 'R', 1, false, false);
        level.moveCar(level.getVehiclesMap().get('d'), 'U', 2, false, false);
        level.moveCar(level.getVehiclesMap().get('e'), 'U', 3, false, false);
        level.moveCar(level.getVehiclesMap().get('f'), 'L', 2, false, false);
        level.moveCar(level.getVehiclesMap().get('g'), 'L', 3, false, false);
        level.moveCar(level.getRedCar(), 'D', 4, false, false);
        assertTrue(level.getRedCar().isOnGoal());
    }

    @ParameterizedTest
    @DisplayName("Test on different moves")
    @CsvSource({
        
            "6, U, 1, false",
            "7, D, 1, false",
            "15, L, 1, false"
    })
    void test6(int testNumber, char direction, int distance,
            boolean isMuted) throws LevelNotFoundException, WrongLevelFormatException {
        level = new Level(usableLevels + "/level_1.txt");
        boolean success = level.moveCar(level.getRedCar(), direction, distance, false, false);
        assertFalse(success);
    }

    @Test
    @DisplayName("Movement right against another car")
    void test7() throws LevelNotFoundException, WrongLevelFormatException {
        level = new Level(usableLevels + "/level_1.txt");
        boolean success = level.moveCar(level.getVehiclesMap().get('a'), 'R', 1, false, false);
        assertFalse(success);
    }

    @Test
    @DisplayName("Movement left against another car")
    void test8() throws LevelNotFoundException, WrongLevelFormatException {
        level = new Level(usableLevels + "/level_1.txt");
        boolean success = level.moveCar(level.getVehiclesMap().get('b'), 'L', 1, false, false);
        assertFalse(success);
    }

    @Test
    @DisplayName("Movement up against the wall")
    void test9() throws LevelNotFoundException, WrongLevelFormatException {
        level = new Level(usableLevels + "/level_1.txt");
        boolean success = level.moveCar(level.getVehiclesMap().get('c'), 'U', 1, false, false);
        assertFalse(success);
    }

    @Test
    @DisplayName("Movement down against the wall")
    void test10() throws LevelNotFoundException, WrongLevelFormatException {
        level = new Level(usableLevels + "/level_1.txt");
        boolean success = level.moveCar(level.getVehiclesMap().get('d'), 'D', 2, false, false);
        assertFalse(success);
    }

    @Test
    @DisplayName("Movement right against the wall")
    void test11() throws LevelNotFoundException, WrongLevelFormatException {
        level = new Level(usableLevels + "/level_1.txt");
        boolean success = level.moveCar(level.getVehiclesMap().get('f'), 'R', 2, false, false);
        assertFalse(success);
    }

    @Test
    @DisplayName("Movement left against the wall")
    void test12() throws LevelNotFoundException, WrongLevelFormatException {
        level = new Level(usableLevels + "/level_1.txt");
        boolean success = level.moveCar(level.getVehiclesMap().get('a'), 'L', 1, false, false);
        assertFalse(success);
    }

    @Test
    @DisplayName("Horizontal movement on a vertical car")
    void test13() throws LevelNotFoundException, WrongLevelFormatException {
        level = new Level(usableLevels + "/level_1.txt");
        boolean success = level.moveCar(level.getVehiclesMap().get('g'), 'U', 1, false, false);
        assertFalse(success);
    }

    @Test
    @DisplayName("Test on reset functionality")
    void test14()throws LevelNotFoundException, WrongLevelFormatException{
        level = new Level(usableLevels + "/level_1.txt");

        char[][] parking= new char[level.getBoard().getNRows()][level.getBoard().getNRows()];
        for(int i = 0; i< level.getBoard().getNRows(); i++){
            System.arraycopy(level.getBoard().getTiles()[i], 0, parking[i], 0, 8);
        }

        level.moveCar(level.getVehiclesMap().get('c'), 'D', 1, false, false);
        level.moveCar(level.getVehiclesMap().get('b'), 'R', 1, false, false);
        level.moveCar(level.getVehiclesMap().get('a'), 'R', 1, false, false);
        level.moveCar(level.getVehiclesMap().get('d'), 'U', 2, false, false);
        level.moveCar(level.getVehiclesMap().get('e'), 'U', 3, false, false);
        
        level.reset();
        assertArrayEquals(parking, level.getBoard().getTiles());
    }

    @Test
    @DisplayName("Test on reset functionality")
    void test15() throws LevelNotFoundException, WrongLevelFormatException{
        level = new Level(usableLevels + "/level_1.txt");
        level.moveCar(level.getVehiclesMap().get('c'), 'D', 1, false, false);
        String expectedBoard = "++++++++\n" +
                               "+aabbb +\n" +
                               "+   * c+\n" +
                               "+d  * c+\n" +
                               "+d fff +\n" +
                               "+de    +\n" +
                               "+ e ggg+\n" +
                               "++++@+++\n";
        assertEquals(expectedBoard, level.getBoard().toString());
    }

}