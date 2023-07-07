package es.upm.pproject.parkingjam.models;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class GameTest {
    Game g;
    Level level;

    boolean moveCar(Car car, char direction, int distance){
        return level.moveCar(car, direction, distance, false, false);
    }
    
    @BeforeEach
    void setUp(){
        g = new Game();
    }

    @Test
    void test1(){
        level = g.getLevel();
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
    void test2(){
        g.levelPathFormat = "src/main/resources/levels4GameTest/level_%d.txt";
        //g.path = "src/main/resources/levels4GameTest";
        g.newGame();
        assertEquals("Level 2", g.getLevelName());
    }



}
