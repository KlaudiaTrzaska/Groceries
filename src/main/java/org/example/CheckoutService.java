package org.example;

import java.util.ArrayList;
import java.util.Arrays;

public class CheckoutService {

    private ArrayList<Product> products = new ArrayList<Product>(Arrays.asList(
            new Product("water", 3),
            new Product("bread", 6.5),
            new Product("butter", 10),
            new Product("tomato", 2),
            new Product("yogurt", 2.5),
            new Product("coke", 3.2),
            new Product("chocolate bar", 4.2)
    ));

    public double toPay(ArrayList<String> products) {

        double sum = 0;
        for (String product : products) {
            sum += getPriceForProduct(product);
        }
        return sum;
    }

    public double getPriceForProduct(String productName) {
        for (Product product : products) {
            if (product.getProductName() == productName) {
                return product.getPrice();
            }
        }
        return 0.0;
    }
}
