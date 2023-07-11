package es.upm.pproject.parkingjam.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class to test the Coordinates")
class CoordinatesTest {
    private Coordinates coords;
    
    @BeforeEach
    void init(){
        coords = new Coordinates(32,12);
    }

    @Test
    @DisplayName("Tests the Coordinates constructor")
    void test1(){
        assertEquals(32, coords.getX());
        assertEquals(12, coords.getY());
    }

    @Test
    @DisplayName("Override hashCode test")
    void test2(){
        assertEquals(((Object)coords).hashCode(), coords.hashCode());
    }

    @Test
    @DisplayName("Override equals test")
    void equalsTest(){
        int[] intCoords = new int[2];
        Coordinates nullCoords = null;
        assertNotEquals(coords, nullCoords);
        assertNotEquals(coords, intCoords);
        assertEquals(coords, coords);
        assertEquals(coords, new Coordinates(32, 12));
        Coordinates coords2 = new Coordinates(32, 64);
        assertNotEquals(coords, coords2);
    }
}

