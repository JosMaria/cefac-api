package com.lievasoft.cefac.service.impl;

import com.lievasoft.cefac.dto.product.CreateProductDto;
import com.lievasoft.cefac.dto.product.ProductResponseDto;
import com.lievasoft.cefac.mapper.ProductMapper;
import com.lievasoft.cefac.preparer.ImagePreparer;
import com.lievasoft.cefac.preparer.ProductPreparer;
import com.lievasoft.cefac.repository.ImageRepository;
import com.lievasoft.cefac.repository.ProductRepository;
import com.lievasoft.cefac.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;
    private final ProductPreparer productPreparer;
    private final ImagePreparer imagePreparer;
    private final ProductMapper productMapper;
    private final ImageRepository imageRepository;

    @Override
    public ProductResponseDto create(final CreateProductDto payload) {
        var productToPersist = productPreparer.prepare(payload);
        var persistedProduct = productRepository.save(productToPersist);
        return productMapper.mapToProductResponseDto(persistedProduct);
    }

    @Override
    public UUID uploadImageToFileSystem(final Long productId, final UUID variantId, final MultipartFile file) {
        var productObtained = productRepository.findByIdWithVariants(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        var variantObtained = productObtained.getVariants().stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Variant not found"));

        var imageToPersist = imagePreparer.prepare(file);
        var imagePersisted = imageRepository.save(imageToPersist);
        variantObtained.setImage(imagePersisted);
        return imagePersisted.getId();
    }

//    private void saveImageToFileSystem(String imageId, MultipartFile file, Path directory) throws IOException {
//        Path filePath = directory.resolve(imageId);
//        Files.write(filePath, file.getBytes());
//        log.info("Image with ID '{}' uploaded successfully in the folder {}", imageId, filePath.toAbsolutePath());
//    }
}
