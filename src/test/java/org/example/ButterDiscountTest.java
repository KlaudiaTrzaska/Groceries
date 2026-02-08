package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;


class ButterDiscountTest {
    
    ButterDiscount underTest;
    
    @BeforeEach
    public void init(){
        underTest = new ButterDiscount();
    }
    
    @Test
    public void testDiscountForFiveButters(){

        ArrayList<String> cart = new ArrayList<>(Arrays.asList(
                "butter", "butter", "butter", "butter", "butter"
        ));
        assertEquals(40,underTest.countDiscount(cart));
    }

    @Test
    public void testNoDiscountForButter(){

        ArrayList<String> cart = new ArrayList<>(Arrays.asList(
                "butter", "butter"
        ));
        assertEquals(20,underTest.countDiscount(cart));
    }
}