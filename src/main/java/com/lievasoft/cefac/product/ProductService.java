package com.lievasoft.cefac.product;

import com.lievasoft.cefac.product.dto.ProductCreateDto;
import com.lievasoft.cefac.product.dto.ProductResponse;

public interface ProductService {

    ProductResponse create(ProductCreateDto payload);
}
