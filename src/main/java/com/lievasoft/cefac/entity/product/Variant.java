package com.lievasoft.cefac.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
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

    @OneToOne(orphanRemoval = true)
    private Image image;

    public Variant(double price, double quantity) {
        this.price = price;
        this.quantity = quantity;
    }
}
