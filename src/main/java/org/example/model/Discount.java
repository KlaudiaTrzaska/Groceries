package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;

@Entity
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "product_name")
    @Getter
    @Setter
    private String productName;

    @Column
    @Getter
    @Setter
    private BigDecimal threshold;

    @Column(name = "type_of_discount")
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private DiscountTypes discountTypes;

    @Column
    @Getter
    @Setter
    private Double discount;
}
