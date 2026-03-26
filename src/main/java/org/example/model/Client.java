package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "client_name")
    @Getter
    @Setter
    private String clientName;

    @Column(name = "has_card")
    @Getter
    @Setter
    private boolean hasLoyaltyCard;

    @Column
    @Getter
    @Setter
    private int points;
}
