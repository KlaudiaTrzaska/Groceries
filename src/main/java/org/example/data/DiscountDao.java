package org.example.data;

import org.example.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountDao extends JpaRepository<Discount, String> {

    Optional<Discount> getDiscountByProductName(String productName);
}
