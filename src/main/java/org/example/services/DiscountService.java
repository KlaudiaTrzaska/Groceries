package org.example.services;

import org.example.data.DiscountDao;
import org.example.model.Discount;
import org.example.model.DiscountTypes;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    private final DiscountDao discountDao;

    public DiscountService(DiscountDao discountDao) {
        this.discountDao = discountDao;
    }

    public void addDiscount(String productName, int threshold, DiscountTypes discountTypes, double discount) {
        Discount discount1 = new Discount();
        discount1.setProductName(productName);
        discount1.setThreshold(threshold);
        discount1.setDiscountTypes(discountTypes);
        discount1.setDiscount(discount);
        discountDao.save(discount1);
    }

    public static double countDiscount(Discount product, int quantity, double price) {

        if (quantity < product.getThreshold()) {
            return quantity * price;
        }

        return switch (product.getDiscountTypes()) {
            case percentage -> {
                yield  quantity * price * (1 - product.getDiscount());
            }
            case gratis -> {
                yield (quantity - (double) quantity / product.getThreshold()) * price;
            }
        };
    }

}
