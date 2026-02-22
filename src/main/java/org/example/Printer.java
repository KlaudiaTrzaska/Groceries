package org.example;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

public class Printer {

    public static void printAReceipt(Receipt receipt) {

        System.out.println("--- CHECKOUT SUMMARY ---");

        for (Map.Entry<String, Integer> receiptEntry : receipt.productsMap.entrySet()) {
            double price = Product.getPriceByName(receiptEntry.getKey());
            System.out.printf("%-15s : %.2f PLN \n", receiptEntry.getKey(), price * receiptEntry.getValue());
        }


        System.out.println("------------------------");
        System.out.printf("BEFORE DISCOUNT:          %.2f PLN \n", receipt.totalPrice + receipt.totalDiscounts);
        System.out.printf("SUM OF DISCOUNTS:         %.2f PLN \n", receipt.totalDiscounts);
        System.out.printf("TO PAY:                   %.2f PLN \n", receipt.totalPrice);

    }
}
