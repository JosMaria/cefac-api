package com.lievasoft.cefac.service;

import com.lievasoft.cefac.dto.product.CreateProductDto;
import com.lievasoft.cefac.dto.product.ProductResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ProductService {

    ProductResponseDto create(CreateProductDto payload);

    UUID uploadImageToFileSystem(Long id, UUID variantId, MultipartFile file);
}
