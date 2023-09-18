package com.api.adega.api.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ImageStorageConfig {

    @Value("${image.upload.directory}")
    private String uploadDirectory;
}
