package org.example;

import org.example.data.ProductDao;
import org.example.services.ProductService;

import java.util.Map;

public class Printer {

    ProductDao productDao;

    public Printer() {
        this.productDao = new ProductDao();
    }

    public void printAReceipt(Receipt receipt) {

        System.out.println("--- CHECKOUT SUMMARY ---");

        for (Map.Entry<String, Integer> receiptEntry : receipt.productsMap.entrySet()) {
            double price = productDao.getPriceByName(receiptEntry.getKey()).get();
            System.out.printf("%-15s : %.2f PLN \n", receiptEntry.getKey(), price * receiptEntry.getValue());
        }


        System.out.println("------------------------");
        System.out.printf("BEFORE DISCOUNT:          %.2f PLN \n", receipt.totalPrice + receipt.totalDiscounts);
        System.out.printf("SUM OF DISCOUNTS:         %.2f PLN \n", receipt.totalDiscounts);
        System.out.printf("TO PAY:                   %.2f PLN \n", receipt.totalPrice);

    }
}
