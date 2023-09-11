package com.api.adega.api.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PathConfig {

    @Value("${app.path.arquivos}")
    private String pathFiles;

    public String getPathFiles() {
        return pathFiles;
    }
}
