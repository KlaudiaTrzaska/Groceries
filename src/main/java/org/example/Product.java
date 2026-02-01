package org.example;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Product {

    @Getter
    @Setter
    String productName;

    @Getter
    @Setter
    double price;



}
