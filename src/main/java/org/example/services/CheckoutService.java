package org.example.services;

import org.example.ButterDiscount;
import org.example.Discounts;
import org.example.Receipt;
import org.example.TomatoDiscount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CheckoutService {

    ProductService productService;

    public CheckoutService() {
        this.productService = new ProductService();
    }

    ArrayList<Discounts> discounts = new ArrayList<>(Arrays.asList(
            new ButterDiscount(),
            new TomatoDiscount()
    ));

    public Receipt checkout(ArrayList<String> basket) {

        Receipt receipt = new Receipt();
        double sum = 0;
        double countDiscounts = 0;

        List<String> discountList = discounts.stream().map(Discounts::productOnPromo).toList();

        for (String product : basket) {
            if (!receipt.getProductsMap().containsKey(product)) {
                receipt.getProductsMap().put(product, 1);
            } else {
                receipt.getProductsMap().replace(product, receipt.getProductsMap().get(product) + 1);
            }

            if (!discountList.contains(product)) {
                sum += productService.getPriceByName(product);
            }
        }

        for (Discounts discount : discounts) {
            countDiscounts += discount.countDiscount(basket);
        }
        receipt.setTotalDiscounts(countDiscounts);
        receipt.setTotalPrice(sum + countDiscounts);

        return receipt;
    }

    public void addNewDiscount(Discounts discounts) {
        this.discounts.add(discounts);
    }
}
