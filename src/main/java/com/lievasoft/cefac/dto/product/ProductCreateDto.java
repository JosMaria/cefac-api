package com.lievasoft.cefac.dto.product;

import java.util.List;

public record ProductCreateDto(
        String name,
        String tag,
        List<Feature> features,
        List<Variant> variants,
        List<Use> uses
) {
}

record Feature(
        String description
) {
}

record Variant(
        double price,
        double quantity
) {
}

record Use(
        String mode,
        String information
) {
}
