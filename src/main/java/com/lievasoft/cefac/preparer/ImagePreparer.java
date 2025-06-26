package com.lievasoft.cefac.preparer;

import com.lievasoft.cefac.entity.product.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagePreparer {

    public Image prepare(final MultipartFile file) {
        return Image.builder()
                .name(file.getOriginalFilename())
                .path("")
                .type(file.getContentType())
                .build();
    }
}
