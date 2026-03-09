package org.example.services;

import org.example.data.DiscountDao;
import org.example.model.Discount;
import org.example.model.DiscountTypes;

import java.math.BigDecimal;

public class DiscountService {

    private final DiscountDao discountDao;

    public DiscountService( ) {
        this.discountDao = new DiscountDao();
    }

    public void addDiscount(String productName, BigDecimal threshold, DiscountTypes discountTypes, double discount) {
        Discount discount1 = new Discount();
        discount1.setProductName(productName);
        discount1.setThreshold(threshold);
        discount1.setDiscountTypes(discountTypes);
        discount1.setDiscount(discount);
        discountDao.save(discount1);
    }
}
