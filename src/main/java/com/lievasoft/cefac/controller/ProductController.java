package com.lievasoft.cefac.controller;

import com.lievasoft.cefac.dto.product.ProductCreateDto;
import com.lievasoft.cefac.dto.product.ProductResponseDto;
import com.lievasoft.cefac.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<ProductResponseDto> save(@RequestBody ProductCreateDto payload) {
        var productResponseDto = service.create(payload);
        return ResponseEntity.ok(productResponseDto);
    }
}
