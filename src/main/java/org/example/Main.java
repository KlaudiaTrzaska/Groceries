package org.example;

import java.util.ArrayList;
import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CheckoutService checkoutService = new CheckoutService();

        ArrayList<String> cart = new ArrayList<>(Arrays.asList(
                "butter", "butter", "butter", "butter", "butter",
                "water", "water", "water",
                "tomato", "tomato",
                "bread", "bread", "bread",
                "chocolate bar"
        ));

        checkoutService.addNewDiscount(new BreadDiscount());
        System.out.println(String.format("I'm gonna pay: %2f", checkoutService.toPay(cart)));
        Printer.printAReceipt(cart);
    }
}