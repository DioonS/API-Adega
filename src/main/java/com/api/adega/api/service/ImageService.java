package com.api.adega.api.service;

import com.api.adega.api.exception.ImageUploadException;
import com.api.adega.api.model.Image;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class ImageService {

    private static final String IMAGE_UPLOAD_DIRECTORY = "C:\\Users\\diego.dionisio\\Pictures\\directory";

    public void saveImage(Image image) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(image.getProductImage());
            File file = new File(IMAGE_UPLOAD_DIRECTORY + image.getProductImageFileName());
            try (FileOutputStream fos = new FileOutputStream(file)){
                fos.write(imageBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ImageUploadException("Falha no Upload da imagem: " + e.getMessage());
        }
    }
}