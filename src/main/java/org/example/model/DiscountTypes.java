package org.example.model;

public enum DiscountTypes {
    percentage, gratis;

    @Override
    public String toString(){
        return name().toLowerCase();
    }
}
