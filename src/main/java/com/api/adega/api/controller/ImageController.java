package com.api.adega.api.controller;

import com.api.adega.api.model.Image;
import com.api.adega.api.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestBody Image image) {
        imageService.saveImage(image);
        return new ResponseEntity<>("Imagem carregada com sucesso", HttpStatus.CREATED);
    }
}
