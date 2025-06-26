package com.lievasoft.cefac.service.impl;

import com.lievasoft.cefac.dto.product.CreateProductDto;
import com.lievasoft.cefac.dto.product.ProductResponseDto;
import com.lievasoft.cefac.mapper.ProductMapper;
import com.lievasoft.cefac.preparer.ProductPreparer;
import com.lievasoft.cefac.repository.ProductRepository;
import com.lievasoft.cefac.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;
    private final ProductPreparer productPreparer;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDto create(final CreateProductDto payload) {
        var productToPersist = productPreparer.prepare(payload);
        var persistedProduct = productRepository.save(productToPersist);
        return productMapper.mapToProductResponseDto(persistedProduct);
    }
}
