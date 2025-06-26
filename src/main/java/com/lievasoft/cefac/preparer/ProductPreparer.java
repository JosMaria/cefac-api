package com.lievasoft.cefac.preparer;

import com.lievasoft.cefac.dto.product.CreateProductDto;
import com.lievasoft.cefac.entity.product.Feature;
import com.lievasoft.cefac.entity.product.Product;
import com.lievasoft.cefac.entity.product.Usage;
import com.lievasoft.cefac.entity.product.Variant;
import com.lievasoft.cefac.mapper.CollectionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductPreparer {

    private final CollectionMapper collectionMapper;

    public Product prepare(final CreateProductDto payload) {
        var productToPersist = Product.builder()
                .name(payload.name())
                .tag(payload.tag())
                .measure(payload.measure())
                .note(payload.note())
                .warning(payload.warning())
                .build();

        var features = collectionMapper.transform(payload.features(), Feature::new);

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
