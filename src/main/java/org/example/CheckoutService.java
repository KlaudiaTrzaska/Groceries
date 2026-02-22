package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.Product.getPriceByName;

public class CheckoutService {

        ArrayList<Discounts> discounts = new ArrayList<>(Arrays.asList(
                new ButterDiscount(),
                new TomatoDiscount()
        ));

    public Receipt checkout(ArrayList<String> basket) {

        Receipt receipt = new Receipt();
        double sum = 0;
        double countDiscounts = 0;

      List<String> discountList = discounts.stream().map(Discounts::productOnPromo).toList();

       for (String product : basket){
           if (!receipt.productsMap.containsKey(product)){
               receipt.productsMap.put(product, 1);
           } else {
               receipt.productsMap.replace(product, receipt.productsMap.get(product)+1);
           }

           if (!discountList.contains(product)){
               sum += getPriceByName(product);
           }
       }

       for (Discounts discount : discounts){
           countDiscounts += discount.countDiscount(basket);
       }
       receipt.totalDiscounts = countDiscounts;
       receipt.totalPrice = sum +countDiscounts;

        return receipt;
    }

    public void addNewDiscount(Discounts discounts) {
        this.discounts.add(discounts);
    }
}
