package com.lievasoft.cefac.repository;

import com.lievasoft.cefac.entity.product.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"variants"})
    @Query("""
        SELECT p
        FROM Product p
        WHERE p.id = :id
    """)
    Optional<Product> findByIdWithVariants(@Param("id") Long id);
}
