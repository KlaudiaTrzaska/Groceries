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

    public double toPay(ArrayList<String> basket) {
        double sum = 0;

      List<String> discountList = discounts.stream().map(Discounts::productOnPromo).toList();

       for (String product : basket){

           if (!discountList.contains(product)){
               sum += getPriceByName(product);
           }
       }

       for (Discounts discount : discounts){
           sum += discount.countDiscount(basket);
       }
        return sum;
    }
}
