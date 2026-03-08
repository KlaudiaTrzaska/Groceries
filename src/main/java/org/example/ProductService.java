package org.example;


import org.example.data.ProductDao;
import org.example.model.Product;

public class ProductService {

    private final ProductDao productDao;

    public ProductService() {
        this.productDao = new ProductDao();
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