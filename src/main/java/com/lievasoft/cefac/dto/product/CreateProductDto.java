package com.lievasoft.cefac.dto.product;

import com.lievasoft.cefac.entity.product.Measure;

import java.util.List;

public record CreateProductDto(
        String name,
        String tag,
        Measure measure,
        String note,
        String warning,
        List<FeatureDto> features,
        List<VariantDto> variants,
        List<UsageDto> usages
) {
    public record FeatureDto(String description) {}

    public record VariantDto(double price, double quantity) {}

    public record UsageDto(String mode, String information) {}
}
