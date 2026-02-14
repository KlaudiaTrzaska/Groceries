package org.example;

import java.util.List;

public class Printer {

    public static void printAReceipt(List<String> basket) {

        double totalPrice = 0;
        System.out.println("--- CHECKOUT SUMMARY ---");

        for (String productName : basket) {
            double price = Product.getPriceByName(productName);
            System.out.printf("%-15s : %.2f PLN \n", productName, price);

            totalPrice += price;
        }
        System.out.println("------------------------");
        System.out.printf("TOTAL:          %.2f PLN \n", totalPrice);
    }
}
