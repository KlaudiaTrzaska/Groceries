package org.example.services;

import org.example.ProductService;

import java.util.ArrayList;

public class Printer {

    public static void printAReceipt(ArrayList<String> basket) {

        ProductService productService = new ProductService();
        CheckoutService service = new CheckoutService();
        double totalPrice = 0;
        double priceWithDiscounts = service.toPay(basket);

        System.out.println("--- CHECKOUT SUMMARY ---");

        for (String productName : basket) {
            double price = productService.getPriceByName(productName);
            System.out.printf("%-15s : %.2f PLN \n", productName, price);

            totalPrice += price;
        }

        double discountSum = totalPrice - priceWithDiscounts;

        System.out.println("------------------------");
        System.out.printf("BEFORE DISCOUNT:          %.2f PLN \n", totalPrice);
        System.out.printf("SUM OF DISCOUNTS:         %.2f PLN \n", discountSum);
        System.out.printf("TO PAY:                   %.2f PLN \n", priceWithDiscounts);

    }
}
