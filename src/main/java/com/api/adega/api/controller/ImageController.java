package com.api.adega.api.controller;

import com.api.adega.api.config.ImageStorageConfig;
import com.api.adega.api.entities.Image;
import com.api.adega.api.repository.ImageRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/image")
@CrossOrigin()
public class ImageController {

    @Autowired
    ImageRepo imageRepository;

    @Autowired
    ImageStorageConfig storageConfig;

    @GetMapping(path = {"/{name}"})
    @Operation(summary = "Pesquisar imagem.", description = "Pesquisa imagem por nome.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Pesquisa realizada com sucesso!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Image.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)
    })
    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) {

        final Optional<Image> dbImage = imageRepository.findByName(name);

        if (dbImage.isPresent()) {
            String imagePath = storageConfig.getUploadDirectory() + File.separator + name;

            try {
                byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));

                return ResponseEntity
                        .ok()
                        .contentType(MediaType.valueOf(dbImage.get().getType()))
                        .body(imageBytes);
            } catch (IOException e) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(("Erro ao ler a imagem: " + e.getMessage()).getBytes());
            }
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @PostMapping("/upload")
    @Transactional
    @Operation(summary = "Adicionar imagem.", description = "Adiciona nova imagem no diretório do sistema")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Imagem adicionada com sucesso!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Image.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content)
    })
    public ResponseEntity<ImageUploadResponse> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {

        String imageName = UUID.randomUUID().toString() + file.getOriginalFilename();
        File imageFile = new File(storageConfig.getUploadDirectory(), imageName);

        file.transferTo(imageFile);

        imageRepository.save(Image.builder()
                .name(imageName)
                .type(file.getContentType())
                .build());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageUploadResponse("Upload da imagem com sucesso: " + imageName));
    }

    @GetMapping("/listaImagens")
    @Operation(summary = "Listagem de todas as imagens.", description = "Lista todas as imagens presentes no diretório")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Listagem gerada com sucesso!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Image.class)) }),
            @ApiResponse(responseCode = "204", description = "Bad Request",
                    content = @Content)
    })
    public ResponseEntity<List<Image>> listImages() {
        List<Image> images = imageRepository.findAll();
        if (!images.isEmpty()) {
            return ResponseEntity
                    .ok()
                    .body(images);
        } else {
            return ResponseEntity
                    .noContent()
                    .build();
        }
    }

    @DeleteMapping("/delete/{name}")
    @Transactional
    @Operation(summary = "Excluir imagem.", description = "Exclui imagem (por nome) do diretorio ")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Exclusão realizada com sucesso!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Image.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)
    })
    public ResponseEntity<String> deleteImage(@PathVariable("name") String name) {
        String imagePath = storageConfig.getUploadDirectory() + File.separator + name;

        File imageFile = new File(imagePath);

        if (imageFile.exists()) {
            if (imageFile.delete()) {
                imageRepository.deleteByName(name);
                return ResponseEntity
                        .ok()
                        .body("Imagem excluida com sucesso: " + name);
            } else {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erro ao excluir a imagem: " + name);
            }
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Imagem não encontrada: " + name);
        }
    }

    @PutMapping("/update/{name}")
    @Operation(summary = "Atualiza imagem.", description = "Atualiza nome ou imagem (por nome) no diretório")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Image.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content)
    })
    public ResponseEntity<String> updateImage(@PathVariable("name") String name, @RequestParam(value = "newName", required = false) String newName, @RequestParam(value = "newImage", required = false) MultipartFile newImage) throws IOException {

        String imagePath = storageConfig.getUploadDirectory() + File.separator + name;

        File imageFile = new File(imagePath);

        if (imageFile.exists()) {
            if (newName != null && !newName.isEmpty()) {
                String updateName = newName + "." + FilenameUtils.getExtension(name);
                File updateFile = new File(storageConfig.getUploadDirectory(), updateName);

                if (!imageFile.renameTo(updateFile)) {
                    return ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Erro ao atualizar o nome da imagem: " + name);
                }

                Optional<Image> dbImageOptional = imageRepository.findByName(name);
                if (dbImageOptional.isPresent()) {

                    Image dbImage = dbImageOptional.get();
                    dbImage.setName(updateName);
                    imageRepository.save(dbImage);
                }

                if (newImage != null) {
                    String newImageName = newName != null && !newName.isEmpty() ? newName : name;
                    File newImageFile = new File(storageConfig.getUploadDirectory(), newImageName);
                    newImage.transferTo(newImageFile);
                }

                return ResponseEntity
                        .ok()
                        .body("imagem atualizada com sucesso: " + name);
            }
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("O novo nome não pode estar vazio.");
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Imagem não encontrada: " + name);
    }
}
