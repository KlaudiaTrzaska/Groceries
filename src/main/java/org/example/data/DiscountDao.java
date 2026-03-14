package org.example.data;

import org.example.model.Discount;
import org.hibernate.Session;

import java.util.Optional;

public class DiscountDao extends BaseDao<Discount, String>{
    public DiscountDao() {
        super(Discount.class);
    }

    public Optional<Discount> getDiscountByProductName(String productName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT d FROM Discount d WHERE d.productName = :name", Discount.class)
                    .setParameter("name", productName)
                    .uniqueResultOptional();
        }
    }

}
