package org.example;


import org.example.services.CheckoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CheckoutServiceTest {

    CheckoutService service;
    Printer printer;

    @BeforeEach
    public void init() {
        service = new CheckoutService();
        printer = new Printer();
    }

    @ParameterizedTest
    @MethodSource("basketToCheckButterDiscount")
    public void testDiscountForButters(List<String> products, double expectedPrice) {

        Receipt receipt = service.checkout(new ArrayList<>(products));
        assertEquals(expectedPrice, receipt.totalPrice);
        printer.printAReceipt(receipt);
    }

    private static Stream<Arguments> basketToCheckButterDiscount() {
        return Stream.of(
                Arguments.of(Arrays.asList(
                        "butter",
                        "butter"), 20.0),
                Arguments.of(Arrays.asList(
                        "butter",
                        "butter",
                        "butter"), 24.0),
                Arguments.of(Arrays.asList(
                        "butter"), 10.0)
        );
    }


    @ParameterizedTest
    @MethodSource("productsAndPricesProvider")
    public void testProductsInBasket(List<String> products, double expectedPrice) {
        Receipt receipt = service.checkout(new ArrayList<>(products));
        assertEquals(expectedPrice, receipt.totalPrice);
        printer.printAReceipt(receipt);
    }


    private static Stream<Arguments> productsAndPricesProvider() {
        return Stream.of(
                Arguments.of(Arrays.asList(
                        "yogurt",
                        "coke"), 5.7),
                Arguments.of(Arrays.asList(
                        "yogurt",
                        "yogurt"), 5.0),
                Arguments.of(Arrays.asList(
                        "water",
                        "water",
                        "water",
                        "water",
                        "water"), 15),
                Arguments.of(Arrays.asList(
                        "water",
                        "bread",
                        "butter",
                        "tomato",
                        "yogurt",
                        "coke",
                        "chocolate bar"
                ), 31.4)
        );
    }

    @ParameterizedTest
    @MethodSource("basketToCheckTomatoDiscount")
    public void testDiscountForTomatoes(List<String> products, double expectedPrice) {
        Receipt receipt = service.checkout(new ArrayList<>(products));
        assertEquals(expectedPrice, receipt.totalPrice);
        printer.printAReceipt(receipt);
    }

    private static Stream<Arguments> basketToCheckTomatoDiscount() {
        return Stream.of(
                Arguments.of(Arrays.asList(
                        "tomato",
                        "tomato",
                        "tomato",
                        "tomato",
                        "tomato"), 8.0),
                Arguments.of(Arrays.asList(
                        "tomato",
                        "tomato",
                        "tomato",
                        "tomato"), 8.0));
    }
}