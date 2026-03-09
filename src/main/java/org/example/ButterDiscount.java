package org.example;

import org.example.services.ProductService;

import java.util.ArrayList;

public class ButterDiscount implements Discounts {

    ProductService productService;

    public ButterDiscount() {
        this.productService = new ProductService();
    }

    @Override
    public double countDiscount(ArrayList<String> basket) {

        long butterCounter = basket.stream().filter(product -> product.equals("butter")).count();
        double butterPrice = productService.getPriceByName("butter");
        if (butterCounter >= 3) {
            return butterCounter * butterPrice * 0.8;
        }

        return butterPrice * butterCounter;
    }

    @Override
    public String productOnPromo() {
        return "butter";
    }
}
