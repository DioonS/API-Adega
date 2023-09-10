package com.api.adega.api.service;

import com.api.adega.api.exception.ImageNotFoundException;
import com.api.adega.api.exception.ImageUploadException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadImage(MultipartFile file) throws ImageUploadException;

    byte[] downloadImage(String imagePath) throws ImageNotFoundException;
}
