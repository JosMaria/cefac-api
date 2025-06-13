package com.lievasoft.cefac.service;


import com.lievasoft.cefac.dto.product.ProductCreateDto;
import com.lievasoft.cefac.dto.product.ProductResponseDto;

public interface ProductService {

    ProductResponseDto create(ProductCreateDto payload);
}
