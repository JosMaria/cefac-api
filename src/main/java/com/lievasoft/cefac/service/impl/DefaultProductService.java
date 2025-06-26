package com.lievasoft.cefac.service.impl;

import com.lievasoft.cefac.dto.product.CreateProductDto;
import com.lievasoft.cefac.dto.product.ProductResponseDto;
import com.lievasoft.cefac.entity.product.Feature;
import com.lievasoft.cefac.entity.product.Product;
import com.lievasoft.cefac.entity.product.Usage;
import com.lievasoft.cefac.entity.product.Variant;
import com.lievasoft.cefac.mapper.CollectionMapper;
import com.lievasoft.cefac.mapper.ProductMapper;
import com.lievasoft.cefac.repository.ProductRepository;
import com.lievasoft.cefac.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;
    private final CollectionMapper collectionMapper;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDto create(final CreateProductDto payload) {
        var productToPersist = buildProductToPersist(payload);
        var persistedProduct = productRepository.save(productToPersist);
        return productMapper.mapToProductResponseDto(persistedProduct);
    }

    private Product buildProductToPersist(final CreateProductDto payload) {
        var productToPersist = Product.builder()
                .name(payload.name())
                .tag(payload.tag())
                .measure(payload.measure())
                .note(payload.note())
                .warning(payload.warning())
                .build();

        var features = collectionMapper.transform(
                payload.features(),
                featureDto -> new Feature(featureDto.description())
        );

        var usages = collectionMapper.transform(
                payload.usages(),
                usageDto -> new Usage(usageDto.mode(), usageDto.information())
        );

        var variants = collectionMapper.transform(
                payload.variants(),
                variantDto -> new Variant(variantDto.price(), variantDto.quantity())
        );

        productToPersist.addFeatures(features);
        productToPersist.addUsages(usages);
        productToPersist.addVariants(variants);
        return productToPersist;
    }
}
