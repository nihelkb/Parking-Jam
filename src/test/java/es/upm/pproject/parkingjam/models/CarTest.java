package es.upm.pproject.parkingjam.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.upm.pproject.parkingjam.common.Coordinates;

class CarTest {
    private Car redCar;
    private Car blueCar;

    @BeforeEach
    void init(){
        redCar = new Car(0,0,'*',2, false);
        blueCar = new Car(1,1,'b',3, true);
    }

    @Test
    @DisplayName("Initial position test")
    void test1(){
        blueCar.setInitialStateX(15);
        blueCar.setInitialStateY(6);
        assertEquals(15, blueCar.getInitialStateX());
        assertNotEquals(1, blueCar.getInitialStateY()); 
    }

    @Test
    @DisplayName("Current positions test")
    void test2(){
        redCar.setCurrentPositionX(8);
        redCar.setCurrentPositionY(16);
        assertEquals(8, redCar.getCurrentPositionX());
        assertEquals(16, redCar.getCurrentPositionY()); 
        assertEquals(new Coordinates(8, 16), redCar.getCurrentPos());
    }

    @Test
    @DisplayName("Is car on goal test")
    void test3(){
        blueCar.setOnGoal(true);
        assertFalse(blueCar.isOnGoal());
    }

    @Test
    @DisplayName("Overrided to string test")
    void toStringTest(){
        assertEquals("* 2 V [0,0]", redCar.toString());
    }
    
}