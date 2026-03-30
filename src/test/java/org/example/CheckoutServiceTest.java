package org.example;


import org.example.data.ClientDao;
import org.example.data.DiscountDao;
import org.example.data.ProductDao;
import org.example.model.Discount;
import org.example.model.DiscountTypes;
import org.example.services.CheckoutService;
import org.example.services.LoyaltyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class CheckoutServiceTest {

    @InjectMocks
    CheckoutService service;
    @InjectMocks
    Printer printer;
    @Mock
    ProductDao productDao;
    @Mock
    DiscountDao discountDao;
    @Mock
    LoyaltyService loyaltyService;

    Discount butterDiscount;
    Discount tomatoDiscount;

    @BeforeEach
    public void init() {
        tomatoDiscount = new Discount();
        tomatoDiscount.setProductName("tomato");
        tomatoDiscount.setDiscountTypes(DiscountTypes.gratis);
        tomatoDiscount.setThreshold(5);
        tomatoDiscount.setDiscount(1.);

        butterDiscount = new Discount();
        butterDiscount.setProductName("butter");
        butterDiscount.setDiscountTypes(DiscountTypes.percentage);
        butterDiscount.setThreshold(3);
        butterDiscount.setDiscount(0.2);
    }

    @ParameterizedTest
    @MethodSource("basketToCheckButterDiscount")
    public void testDiscountForButters(List<String> products, double expectedPrice) {
        lenient().when(discountDao.getDiscountByProductName("butter")).thenReturn(Optional.of(butterDiscount));
        lenient().when(productDao.getPriceByName("butter")).thenReturn(Optional.of(10.0));
        lenient().when(loyaltyService.isClientLoyal(anyString())).thenReturn(false);

        Receipt receipt = service.checkout(new ArrayList<>(products), "");
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
        lenient().when(productDao.getPriceByName("butter")).thenReturn(Optional.of(10.0));
        lenient().when(productDao.getPriceByName("coke")).thenReturn(Optional.of(3.2));
        lenient().when(productDao.getPriceByName("yogurt")).thenReturn(Optional.of(2.5));
        lenient().when(productDao.getPriceByName("water")).thenReturn(Optional.of(3.0));
        lenient().when(productDao.getPriceByName("chocolate bar")).thenReturn(Optional.of(4.2));
        lenient().when(productDao.getPriceByName("bread")).thenReturn(Optional.of(6.5));
        lenient().when(productDao.getPriceByName("tomato")).thenReturn(Optional.of(2.0));
        Receipt receipt = service.checkout(new ArrayList<>(products), "");
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
        lenient().when(discountDao.getDiscountByProductName("tomato")).thenReturn(Optional.of(tomatoDiscount));
        lenient().when(productDao.getPriceByName("tomato")).thenReturn(Optional.of(2.0));
        Receipt receipt = service.checkout(new ArrayList<>(products), "");
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