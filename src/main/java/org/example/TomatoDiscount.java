package org.example;

import java.util.ArrayList;

public class TomatoDiscount implements Discounts {

    @Override
    public double countDiscount(ArrayList<String> basket) {

        long tomatoCounter = basket.stream().filter(product -> product.equals("tomato")).count();
        double tomatoPrice = Product.getPriceByName("tomato");
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
