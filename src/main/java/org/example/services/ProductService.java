package org.example.services;


import org.example.data.ProductDao;
import org.example.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public double getPriceByName(String productName) {
        return productDao.getPriceByName(productName).orElseThrow();
    }

    public void addProduct(String name, double price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        productDao.save(product);
    }
}