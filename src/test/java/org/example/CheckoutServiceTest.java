package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CheckoutServiceTest {

    CheckoutService service;

    @BeforeEach
    public void init() {
        service = new CheckoutService();
    }

    @Test
    public void testToPay() {

        ArrayList<String> cart = new ArrayList<>(Arrays.asList(
                "water", "water", "water", "water", "water"
        ));

        assertEquals(15.0, service.toPay(cart), 0);
    }

    @Test
    public void testToPayYogurt() {

        ArrayList<String> cart = new ArrayList<>(Arrays.asList(
                "yogurt", "yogurt"
        ));

        assertEquals(5.0, service.toPay(cart), 0);
    }

    @Test
    public void testToPayYogurtAndCola() {

        ArrayList<String> cart = new ArrayList<>(Arrays.asList(
                "yogurt", "coke"
        ));

        assertEquals(5.7, service.toPay(cart), 0);
    }

    @Test
    public void testToGetDiscountForTwoButters() {

        ArrayList<String> cart = new ArrayList<>(Arrays.asList(
                "butter", "butter"
        ));

        assertEquals(16.0, service.toPay(cart), 0);
    }

    @Test
    public void testToGetDiscountForMoreThanTwoButters() {

        ArrayList<String> cart = new ArrayList<>(Arrays.asList(
                "butter", "butter", "butter"
        ));

        assertEquals(24.0, service.toPay(cart), 0);
    }

    @Test
    public void testOneButterGetsNoDiscount() {

        ArrayList<String> cart = new ArrayList<>(Arrays.asList(
                "butter"
        ));

        assertEquals(10.0, service.toPay(cart), 0);
    }

    @Test
    public void testGetOneTomatoForFreeIfYouBuyFive() {

        ArrayList<String> cart = new ArrayList<>(Arrays.asList(
                "tomato", "tomato", "tomato", "tomato", "tomato"
        ));

        assertEquals(8.0, service.toPay(cart), 0);
    }

    @Test
    public void testNoDiscountIfLessThanFiveTomatoes() {

        ArrayList<String> cart = new ArrayList<>(Arrays.asList(
                "tomato", "tomato", "tomato", "tomato"
        ));

        assertEquals(8.0, service.toPay(cart), 0);
    }
}