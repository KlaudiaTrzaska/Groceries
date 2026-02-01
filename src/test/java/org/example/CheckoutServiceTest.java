package org.example;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class CheckoutServiceTest extends TestCase {

    @Before
    public void initProducts() {

    }


    CheckoutService service = new CheckoutService();

    @Test
    public void testToPay() {

        ArrayList<String> cart = new ArrayList<>(Arrays.asList(
                "water", "water", "water", "water", "water"
        ));

        Assert.assertEquals(15.0, service.toPay(cart), 0);

    }

    @Test
    public void testToPayYogurt() {

        ArrayList<String> cart = new ArrayList<>(Arrays.asList(
                "yogurt", "yogurt"
        ));

        Assert.assertEquals(5.0, service.toPay(cart), 0);

    }

    @Test
    public void testToPayYogurtAndCola() {

        ArrayList<String> cart = new ArrayList<>(Arrays.asList(
                "yogurt", "coke"
        ));

        Assert.assertEquals(5.7, service.toPay(cart), 0);

    }
}