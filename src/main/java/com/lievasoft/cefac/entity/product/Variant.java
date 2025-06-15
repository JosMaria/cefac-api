package com.lievasoft.cefac.entity.product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@NoArgsConstructor
@Entity
@Table(name = "variants")
public class Variant {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    private Product product;

    private Double price;

    private Double quantity;

    public Variant(double price, double quantity) {
        this.price = price;
        this.quantity = quantity;
    }
}
