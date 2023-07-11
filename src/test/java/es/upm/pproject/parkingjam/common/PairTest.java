package es.upm.pproject.parkingjam.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PairTest {
    private Pair<Integer, Integer> pair1;
    private Pair<Integer, Integer> pair2;

    @BeforeEach
    void init(){
        pair1 = new Pair<>(18,106);
        pair2 = new Pair<>(pair1);
    }

    @Test
    @DisplayName("Tests the first Pair constructor")
    void test1(){
        assertEquals(18, pair1.getLeft());
        assertEquals(106, pair1.getRight());
    }

    @Test
    @DisplayName("Tests the second Pair constructor")
    void test2(){
        assertEquals(18, pair2.getLeft());
        assertEquals(106, pair2.getRight());
    }
}
