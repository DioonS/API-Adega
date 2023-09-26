package com.api.adega.api.service.impl;

import com.api.adega.api.entities.Image;
import com.api.adega.api.entities.Product;
import com.api.adega.api.exception.ImageNotFoundException;
import com.api.adega.api.repository.ImageRepo;
import com.api.adega.api.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepo imageRepo;

    @Value("${image.upload.directory}")
    private String imageUploadDirectory;

    @Override
    public Image getImageForProduct(Product product) {
        Optional<Image> imageOptional = imageRepo.findById(Long.valueOf(product.getProductId()));

        if (imageOptional.isPresent()) {
            return imageOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public void saveImage(Image image, MultipartFile imageFile) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString() + "-" + imageFile.getOriginalFilename();

        Path targetPath = Path.of(imageUploadDirectory, uniqueFileName);

        Files.copy(imageFile.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        image.setName(uniqueFileName);
        imageRepo.save(image);
    }

    @Override
    public Image getImageById(Long id) {
        Optional<Image> imageOptional = imageRepo.findById(id);
        if (imageOptional.isPresent()) {
            return imageOptional.get();
        } else {
            throw new ImageNotFoundException("Imagem não encontrada com o ID: " + id);
        }
    }
}
