package com.api.adega.api.implementation;

import com.api.adega.api.configurations.PathConfig;
import com.api.adega.api.exception.ImageNotFoundException;
import com.api.adega.api.exception.ImageUploadException;
import com.api.adega.api.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageServiceImpl implements ImageService {

    private final PathConfig pathConfig;

    @Autowired
    public ImageServiceImpl(PathConfig pathConfig) {
        this.pathConfig = pathConfig;
    }

    @Override
    public String uploadImage(MultipartFile file) throws ImageUploadException {
        try {
            String fileName = file.getOriginalFilename();
            String filePath = Paths.get(pathConfig.getPathFiles(), fileName).toString();

            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new ImageUploadException("Falha no Upload da imagem: " + e.getMessage());
        }
    }

    @Override
    public byte[] downloadImage(String imagePath) throws ImageNotFoundException {
        try {
            File file = new File(Paths.get(pathConfig.getPathFiles(), imagePath).toString());
            if (!file.exists()) {
                throw new ImageNotFoundException("Imagem não encontrada!");
            }
            return FileCopyUtils.copyToByteArray(file);
        } catch (IOException e) {
            throw new ImageNotFoundException("Falha no download da imagem: " + e.getMessage());
        }
    }
}