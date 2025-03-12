package com.lievasoft.bio.resource;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductResource {

    public record Product(Integer id, String name, double price) {}

    List<Product> products = new ArrayList<>(
            List.of(
                    new Product(1, "shampoo", 30.2),
                    new Product(2, "cellphone", 300.8)
            )
    );

    @GetMapping
    public List<Product> getProducts() {
        return products;
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        products.add(product);
        return product;
    }
}
