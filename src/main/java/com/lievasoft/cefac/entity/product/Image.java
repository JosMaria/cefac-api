package com.lievasoft.cefac.entity.product;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "images")
public class Image {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;

    private String path;

    @Column(length = 50)
    private String type;

    @OneToOne(mappedBy = "image")
    private Variant variant;
}
