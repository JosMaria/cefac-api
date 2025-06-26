package com.lievasoft.cefac.entity.product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Setter
@NoArgsConstructor
@Entity
@Table(name = "features")
public class Feature {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    private Product product;

    private String description;

    public Feature(String description) {
        this.description = description;
    }
}
