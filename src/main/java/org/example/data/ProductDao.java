package org.example.data;

import org.example.model.Product;
import org.hibernate.Session;

import java.util.Optional;

public class ProductDao extends BaseDao<Product, String> {

    public ProductDao() {
        super(Product.class);
    }

    public Optional<Double> getPriceByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT p.price FROM Product p WHERE p.name = :name", Double.class)
                    .setParameter("name", name)
                    .uniqueResultOptional();
        }
    }
}
