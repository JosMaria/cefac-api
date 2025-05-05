package com.lievasoft.cefac.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @GetMapping
    public ResponseEntity<String> getProducts() {
        return ResponseEntity.ok("GET all products");
    }

    @PostMapping
    public ResponseEntity<String> postProducts() {
        return ResponseEntity.ok("POST product");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProducts() {
        return ResponseEntity.ok("DELETE product");
    }
}
