package com.lievasoft.cefac.service.impl;

import com.lievasoft.cefac.dto.product.CreateProductDto;
import com.lievasoft.cefac.dto.product.ProductResponseDto;
import com.lievasoft.cefac.entity.product.Feature;
import com.lievasoft.cefac.entity.product.Product;
import com.lievasoft.cefac.entity.product.Usage;
import com.lievasoft.cefac.entity.product.Variant;
import com.lievasoft.cefac.repository.ProductRepository;
import com.lievasoft.cefac.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponseDto create(final CreateProductDto payload) {
        var productToPersist = Product.builder()
                .name(payload.name())
                .tag(payload.tag())
                .measure(payload.measure())
                .note(payload.note())
                .warning(payload.warning())
                .build();

        List<Feature> featuresToPersist = payload.features()
                .stream()
                .map(featureDto -> new Feature(featureDto.description()))
                .toList();

        List<Usage> usagesToPersist = payload.usages()
                .stream()
                .map(usageDto -> new Usage(usageDto.mode(), usageDto.information()))
                .toList();

        List<Variant> variantsToPersist = payload.variants()
                .stream()
                .map(variantDto -> new Variant(variantDto.price(), variantDto.quantity()))
                .toList();

        productToPersist.addFeatures(featuresToPersist);
        productToPersist.addUsages(usagesToPersist);
        productToPersist.addVariants(variantsToPersist);

        Product persistedProduct = productRepository.save(productToPersist);

        // product to ProductResponseDto
        return new ProductResponseDto(persistedProduct.getName(), persistedProduct.getTag());
    }
}
