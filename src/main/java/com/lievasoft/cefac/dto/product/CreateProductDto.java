package com.lievasoft.cefac.dto.product;

import com.lievasoft.cefac.entity.product.Measure;

import java.util.List;

public record CreateProductDto(
        String name,
        String tag,
        Measure measure,
        String note,
        String warning,
        List<String> features,
        List<VariantDto> variants,
        List<UsageDto> usages
) {
    public record VariantDto(double price, double quantity) {}

    public record UsageDto(String mode, String information) {}
}
