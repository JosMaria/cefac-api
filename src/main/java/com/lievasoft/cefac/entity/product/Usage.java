package com.lievasoft.cefac.entity.product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@NoArgsConstructor
@Entity
@Table(name = "usages")
public class Usage {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    private Product product;

    private String mode;

    private String information;

    public Usage(String mode, String information) {
        this.mode = mode;
        this.information = information;
    }
}
