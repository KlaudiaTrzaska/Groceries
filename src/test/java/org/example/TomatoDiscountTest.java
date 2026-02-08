package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TomatoDiscountTest {

    TomatoDiscount underTest;

    @BeforeEach
    public void init() {
        underTest = new TomatoDiscount();
    }

    @Test
    public void testDiscountForFiveTomatoes() {
        ArrayList<String> cart = new ArrayList<>(Arrays.asList(
                "tomato", "tomato", "tomato", "tomato", "tomato"
        ));
        assertEquals(8, underTest.countDiscount(cart));
    }

    @Test
    public void testNoDiscountFor3Tomatoes() {
        ArrayList<String> cart = new ArrayList<>(Arrays.asList(
                "tomato", "tomato", "tomato"
        ));
        assertEquals(6, underTest.countDiscount(cart));
    }

    @Test
    public void testDiscountFor20Tomatoes() {
        ArrayList<String> cart = new ArrayList<>(Arrays.asList(
                "tomato", "tomato", "tomato", "tomato", "tomato",
                "tomato", "tomato", "tomato", "tomato", "tomato", "tomato",
                "tomato", "tomato", "tomato", "tomato", "tomato", "tomato",
                "tomato", "tomato", "tomato"
        ));
        assertEquals(32, underTest.countDiscount(cart));
    }


}