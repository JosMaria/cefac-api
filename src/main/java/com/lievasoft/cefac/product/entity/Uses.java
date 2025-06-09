package com.lievasoft.cefac.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "uses")
public class Uses {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = LAZY, optional = false)
    private Product product;

    private String mode;

    private String information;
}
