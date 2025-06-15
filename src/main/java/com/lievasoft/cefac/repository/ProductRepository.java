package com.lievasoft.cefac.repository;

import com.lievasoft.cefac.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
