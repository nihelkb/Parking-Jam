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
        coords = new Coordinates(5,9);
    }

    @Test
    @DisplayName("Tests the Coordinates constructor")
    void test1(){
        assertEquals(5, coords.getX());
        assertEquals(9, coords.getY());
    }

    @Test
    @DisplayName("Overrride hashCode test")
    void test2(){
        assertEquals(((Object)coords).hashCode(), coords.hashCode());
    }

    @Test
    @DisplayName("Overrride equals test")
    void equalsTest(){
        int[] coordenadasInt = new int[2];
        Coordinates nil = null;
        assertNotEquals(coords,nil);
        assertNotEquals(coords, coordenadasInt);
        assertEquals(coords, coords);
        assertEquals(coords, new Coordinates(5, 9));
        Coordinates coords2 = new Coordinates(3, 7);
        assertNotEquals(coords, coords2);
    }
}

