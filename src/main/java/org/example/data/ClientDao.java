package org.example.data;

import jakarta.transaction.Transactional;
import org.example.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientDao extends JpaRepository<Client, String > {

    Optional<Client> getClientByPhoneNumber(String phoneNumber);

    @Query("SELECT c.points FROM Client c WHERE c.phoneNumber = :phoneNumber")
    Optional<Integer> getPointsByPhoneNumber(String phoneNumber);

    @Modifying
    @Transactional
    @Query("UPDATE Client c SET c.points = :points WHERE c.phoneNumber = :phoneNumber")
    void updatePointsByPhoneNumber(String phoneNumber, int points);




}
