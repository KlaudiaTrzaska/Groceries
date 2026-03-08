package org.example.services;

import org.example.ButterDiscount;
import org.example.Discounts;
import org.example.ProductService;
import org.example.TomatoDiscount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckoutService {

    public ProductService productService;

    public CheckoutService() {
        productService = new ProductService();
    }
        ArrayList<Discounts> discounts = new ArrayList<>(Arrays.asList(
                new ButterDiscount(),
                new TomatoDiscount()
        ));

    public double toPay(ArrayList<String> basket) {
        double sum = 0;

      List<String> discountList = discounts.stream().map(Discounts::productOnPromo).toList();

       for (String product : basket){

           if (!discountList.contains(product)){
               sum += productService.getPriceByName(product);
           }
       }

       for (Discounts discount : discounts){
           sum += discount.countDiscount(basket);
       }
        return sum;
    }

    public void addNewDiscount(Discounts discounts) {
        this.discounts.add(discounts);
    }
}
