package com.lievasoft.cefac.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(length = 1000)
    private String warning;

    @OneToMany(mappedBy = "product")
    private final List<Feature> features = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private final List<Usage> usages = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private final List<Variant> variants = new ArrayList<>();

    public void addFeatures(List<Feature> features) {
        this.features.addAll(features);
    }

    public void addUsages(List<Usage> usage) {
        this.usages.addAll(usage);
    }

    public void addVariants(List<Variant> variants) {
        this.variants.addAll(variants);
    }
}
