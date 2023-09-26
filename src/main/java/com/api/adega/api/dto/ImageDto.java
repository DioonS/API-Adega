package com.api.adega.api.dto;

import com.api.adega.api.entities.Image;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {

    private MultipartFile imageFile;
    private Image image;
}
