package org.example;

import org.example.data.ProductDao;
import org.example.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Printer {

    ProductDao productDao;

    public Printer(ProductDao productDao) {
        this.productDao = productDao;
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
