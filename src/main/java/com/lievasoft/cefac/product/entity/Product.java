package com.lievasoft.cefac.product.entity;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "product_sequence")
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    private Long id;

    private String name;

    private String tag;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Measure measure;

    private String note;

    private String warning;
}
