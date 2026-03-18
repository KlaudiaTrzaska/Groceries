package org.example.data;

import org.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductDao extends JpaRepository<Product, String> {

    @Query("SELECT p.price FROM Product p WHERE p.name = :name")
    Optional<Double> getPriceByName(String name);
}