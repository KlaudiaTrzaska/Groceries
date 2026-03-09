package org.example;

import org.example.services.ProductService;

import java.util.ArrayList;

public class TomatoDiscount implements Discounts {

    ProductService productService;

    public TomatoDiscount() {
        this.productService = new ProductService();
    }

    @Override
    public double countDiscount(ArrayList<String> basket) {

        long tomatoCounter = basket.stream().filter(product -> product.equals("tomato")).count();
        double tomatoPrice = productService.getPriceByName("tomato");
        if (tomatoCounter % 5 == 0) {
            return (tomatoCounter - (double) tomatoCounter /5) * tomatoPrice;
        }
        return tomatoPrice * tomatoCounter;
    }

    @Override
    public String productOnPromo() {
        return "tomato";
    }
}
