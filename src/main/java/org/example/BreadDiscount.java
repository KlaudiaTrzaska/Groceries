package org.example;

import java.util.ArrayList;

public class BreadDiscount implements Discounts{

    ProductService productService;

    public BreadDiscount() {
        this.productService = new ProductService();
    }

    @Override
    public double countDiscount(ArrayList<String> products) {
        long breadCounter = products.stream().filter(product -> product.equals("bread")).count();
        double breadPrice = productService.getPriceByName("bread");
        if (breadCounter >= 3) {
            return breadCounter * breadPrice * 0.8;
        }

        return breadPrice * breadCounter;
    }

    @Override
    public String productOnPromo() {
        return "bread";
    }
}
