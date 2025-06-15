package com.lievasoft.cefac.service;


import com.lievasoft.cefac.dto.product.CreateProductDto;
import com.lievasoft.cefac.dto.product.ProductResponseDto;

public interface ProductService {

    ProductResponseDto create(CreateProductDto payload);
}
