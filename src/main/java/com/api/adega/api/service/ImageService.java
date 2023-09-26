package com.api.adega.api.service;

import com.api.adega.api.entities.Image;
import com.api.adega.api.entities.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    Image getImageForProduct(Product product);
    void saveImage(Image image, MultipartFile imageFile) throws IOException;

    Image getImageById(Long id);

    byte[] getImageBytesById(Long id);

    byte[] getImagesBytesForProduct(Product product);
}
