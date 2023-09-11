package com.api.adega.api.controller;

import com.api.adega.api.configurations.PathConfig;
import com.api.adega.api.exception.ImageNotFoundException;
import com.api.adega.api.exception.ImageUploadException;
import com.api.adega.api.implementation.ImageServiceImpl;
import com.api.adega.api.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/images")
@Slf4j
@CrossOrigin("*")
public class ImageController {

    //@Autowired
    private final ImageService imageService;

    //@Autowired
    private final PathConfig pathConfig;

    @Autowired
    public ImageController(ImageService imageService, PathConfig pathConfig) {
        this.imageService = imageService;
        this.pathConfig = pathConfig;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile file) {
        log.info("Recebendo o arquivo: ", file.getOriginalFilename());
        var routeFile = pathConfig.getPathFiles() + UUID.randomUUID() + "." + extractExtension(file.getOriginalFilename());

        log.info("Novo nome do arquivo: " + routeFile);

        try {
            Files.copy(file.getInputStream(), Path.of(routeFile), StandardCopyOption.REPLACE_EXISTING);
            return new ResponseEntity<>("{\"mensagem\": \"Arquivo carregado com sucesso!\"}", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Erro ao processar arquivo", e);
            return new ResponseEntity<>("{\"mensagem\":\"Erro ao carregar o arquivo!\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String extractExtension(String fileName) {
        int i = fileName.lastIndexOf(".");
        return fileName.substring(i + 1);
    }

    @GetMapping("/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable("imageName") String imageName) {
        try {
            byte[] imageData = imageService.downloadImage(imageName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (ImageNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
