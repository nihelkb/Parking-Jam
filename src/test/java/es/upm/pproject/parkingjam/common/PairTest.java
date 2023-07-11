package es.upm.pproject.parkingjam.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PairTest {
    private Pair<Integer, Integer> pair1;
    private Pair<Integer, Integer> pair2;

    @BeforeEach
    void init(){
        pair1 = new Pair<>(18,106);
        pair2 = new Pair<>(pair1);
    }

    @Test
    @DisplayName("First Pair constructor test")
    void test1(){
        assertEquals(18, pair1.getLeft());
        assertEquals(106, pair1.getRight());
    }

    @Test
    @DisplayName("Second Pair constructor test")
    void test2(){
        assertEquals(18, pair2.getLeft());
        assertEquals(106, pair2.getRight());
    }

    @Test
    @DisplayName("Set methods test")
    void test3(){
        pair1.setLeft(106);
        pair2.setRight(18);
        assertNotEquals(18, pair1.getLeft());
        assertEquals(18, pair2.getRight());
    }

    @Test
    @DisplayName("Overrided to string test")
    void toStringTest(){
        assertEquals("Pair(18,106)", pair2.toString());
    }

}
