package org.example;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;

@AllArgsConstructor
public class Product {

    @Getter
    @Setter
    String productName;

    @Getter
    @Setter
    double price;

    public static double getPriceByName(String productName) {
        ArrayList<Product> products = new ArrayList<Product>(Arrays.asList(
                new Product("water", 3),
                new Product("bread", 6.5),
                new Product("butter", 10),
                new Product("tomato", 2),
                new Product("yogurt", 2.5),
                new Product("coke", 3.2),
                new Product("chocolate bar", 4.2)
        ));

        return products.stream()
                .filter(product -> product.getProductName().equals(productName))
                .findFirst().get().getPrice();
    }
}
