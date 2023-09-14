package com.api.adega.api.controller;

import com.api.adega.api.model.Image;
import com.api.adega.api.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Upload de imagem para o servidor.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Upload da imagem com sucesso.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Image.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
            content = @Content)
    })
    public ResponseEntity<String> uploadImage(@RequestBody Image image) {
        imageService.saveImage(image);
        return new ResponseEntity<>("Imagem carregada com sucesso", HttpStatus.CREATED);
    }
}
