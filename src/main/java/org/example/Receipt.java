package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@AllArgsConstructor
public class Receipt {

    @Getter
    @Setter
    HashMap<String, Integer> productsMap;

    @Getter
    @Setter
    double totalPrice;

    @Getter
    @Setter
    double totalDiscounts;

    public Receipt() {
        this.productsMap = new HashMap<>();
    }
}
