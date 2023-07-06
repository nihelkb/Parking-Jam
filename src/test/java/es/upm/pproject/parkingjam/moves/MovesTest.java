package es.upm.pproject.parkingjam.moves;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.upm.pproject.parkingjam.exceptions.LevelNotFoundException;
import es.upm.pproject.parkingjam.exceptions.WrongLevelFormatException;
import es.upm.pproject.parkingjam.models.Level;

class MovesTest {
    Level level;
    final String usableLevels;
    MovesTest(){
        usableLevels = "src/main/resources/levels";
    }

    @BeforeEach
    void setUP() throws LevelNotFoundException, WrongLevelFormatException{
        level = new Level(usableLevels + "/level_1.txt");
    }

    @Test
    @DisplayName("Movement up is a success")
    void test1() {
        boolean success = level.moveCar(level.getVehiclesMap().get('d'), 'U', 1, false, false);
        assertTrue(success);
    }

    @Test
    @DisplayName("Movement down is a success")
    void test2() {
        boolean success = level.moveCar(level.getVehiclesMap().get('c'), 'D', 1, false, false);
        assertTrue(success);
    }

    @Test
    @DisplayName("Movement right is a success")
    void test3() {
        boolean success = level.moveCar(level.getVehiclesMap().get('f'), 'R', 1, false, false);
        assertTrue(success);
    }

    @Test
    @DisplayName("Movement left is a success")
    void test4() {
        boolean success = level.moveCar(level.getVehiclesMap().get('f'), 'L', 1, false, false);
        assertTrue(success);
    }

    @Test
    @DisplayName("End of level")
    void test5() {
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
     
    @Test
    @DisplayName("Movement up against another car")
    void test6() {
        boolean success = level.moveCar(level.getRedCar(), 'U', 1, false, false);
        assertFalse(success);
    }

    @Test
    @DisplayName("Movement down against another car")
    void test7() {
        boolean success = level.moveCar(level.getRedCar(), 'D', 1, false, false);
        assertFalse(success);
    }

    @Test
    @DisplayName("Movement right against another car")
    void test8() {
        boolean success = level.moveCar(level.getVehiclesMap().get('a'), 'R', 1, false, false);
        assertFalse(success);
    }

    @Test
    @DisplayName("Movement left against another car")
    void test9() {
        boolean success = level.moveCar(level.getVehiclesMap().get('b'), 'L', 1, false, false);
        assertFalse(success);
    }

    @Test
    @DisplayName("Movement up against the wall")
    void test10() {
        boolean success = level.moveCar(level.getVehiclesMap().get('c'), 'U', 1, false, false);
        assertFalse(success);
    }

    @Test
    @DisplayName("Movement down against the wall")
    void test11() {
        boolean success = level.moveCar(level.getVehiclesMap().get('d'), 'D', 2, false, false);
        assertFalse(success);
    }

    @Test
    @DisplayName("Movement right against the wall")
    void test12() {
        boolean success = level.moveCar(level.getVehiclesMap().get('f'), 'R', 2, false, false);
        assertFalse(success);
    }

    @Test
    @DisplayName("Movement left against the wall")
    void test13() {
        boolean success = level.moveCar(level.getVehiclesMap().get('a'), 'L', 1, false, false);
        assertFalse(success);
    }

    @Test
    @DisplayName("Horizontal movement on a vertical car")
    void test14() {
        boolean success = level.moveCar(level.getVehiclesMap().get('g'), 'U', 1, false, false);
        assertFalse(success);
    }

    @Test
    @DisplayName("Vertical movement on a horizontal car")
    void test15() {
        boolean success = level.moveCar(level.getRedCar(), 'L', 1, false, false);
        assertFalse(success);
    }

}
