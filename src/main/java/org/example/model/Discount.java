package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    private int threshold;

    @Column(name = "type_of_discount")
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private DiscountTypes discountTypes;

    @Column
    @Getter
    @Setter
    private Double discount;

    @Override
    public String toString(){
        return ("product name: " + this.productName + " threshold: " + this.threshold + " discount type: " + this.discountTypes + " discount: " + this.discount);
    }
}
