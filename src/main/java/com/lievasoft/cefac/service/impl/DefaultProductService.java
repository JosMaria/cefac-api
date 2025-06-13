package com.lievasoft.cefac.service.impl;

import com.lievasoft.cefac.dto.product.ProductCreateDto;
import com.lievasoft.cefac.dto.product.ProductResponseDto;
import com.lievasoft.cefac.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class DefaultProductService implements ProductService {

    @Override
    public ProductResponseDto create(ProductCreateDto payload) {
        return null;
    }
}
