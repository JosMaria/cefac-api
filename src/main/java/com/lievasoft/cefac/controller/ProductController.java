package com.lievasoft.cefac.controller;

import com.lievasoft.cefac.dto.product.CreateProductDto;
import com.lievasoft.cefac.dto.product.ProductResponseDto;
import com.lievasoft.cefac.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<ProductResponseDto> save(@RequestBody CreateProductDto payload) {
        var productResponseDto = service.create(payload);
        return new ResponseEntity<>(productResponseDto, CREATED);
    }

    @PostMapping(value = "/{productId}/variants/{variantId}/images")
    public ResponseEntity<UUID> uploadImage(
            @PathVariable("productId") Long productId,
            @PathVariable("variantId") UUID variantId,
            @RequestPart("image") MultipartFile file
    ) {
        return new ResponseEntity<>(service.uploadImageToFileSystem(productId, variantId, file), CREATED);
    }
}
