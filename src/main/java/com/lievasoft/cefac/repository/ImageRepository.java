package com.lievasoft.cefac.repository;

import com.lievasoft.cefac.entity.product.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {

}
