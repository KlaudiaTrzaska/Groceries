package org.example.services;

import org.example.Receipt;
import org.example.data.DiscountDao;
import org.example.data.ProductDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CheckoutService {

    ProductDao productDao;
    DiscountDao discountDao;

    public CheckoutService() {
        this.productDao = new ProductDao();
        this.discountDao = new DiscountDao();
    }


    public Receipt checkout(ArrayList<String> basket) {

        Receipt receipt = new Receipt();
        double sum = 0;
        double countDiscounts = 0;

        HashMap<String, Integer> basketMap = createBasketMap(basket);
        receipt.setProductsMap(basketMap);

        for (Map.Entry<String, Integer> product : basketMap.entrySet()) {

            if (discountDao.getDiscountByProductName(product.getKey()).isEmpty()) {
                sum += productDao.getPriceByName(product.getKey()).get() * product.getValue();
            } else {
                countDiscounts += DiscountService.countDiscount(
                        discountDao.getDiscountByProductName(product.getKey()).get(),
                        product.getValue(), productDao.getPriceByName(product.getKey()).get());
            }
        }
        receipt.setTotalDiscounts(countDiscounts);
        receipt.setTotalPrice(sum + countDiscounts);

        return receipt;
    }

    public HashMap<String, Integer> createBasketMap(ArrayList<String> basket) {
        HashMap<String, Integer> basketMap = new HashMap<>();

        for (String product : basket) {
            if (!basketMap.containsKey(product)) {
                basketMap.put(product, 1);
            } else {
                basketMap.replace(product, basketMap.get(product) + 1);
            }
        }
        return basketMap;
    }
}
