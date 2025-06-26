package com.lievasoft.cefac.mapper;

import com.lievasoft.cefac.dto.product.ProductResponseDto;
import com.lievasoft.cefac.entity.product.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public ProductResponseDto mapToProductResponseDto(Product product) {
        return new ProductResponseDto(product.getName(), product.getTag());
    }
}
