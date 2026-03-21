package org.example.controllers;

import org.example.services.DiscountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CheckoutController {

    DiscountService discountService;

    public CheckoutController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping(value = "/discounts/{productName}")
    public String getDiscountByProductName (@PathVariable String productName) {

        return discountService.checkIfProductHasDiscount(productName);
    }


}
