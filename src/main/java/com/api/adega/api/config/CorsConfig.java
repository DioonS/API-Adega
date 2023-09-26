package com.api.adega.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permite Cors para todas as URLS
                .allowedOrigins("http://localhost:4200") // URL da aplicação permitida
                .allowedOrigins("GET", "POST", "PUT", "DELETE")  // Metodos Http permitidos
                .allowedHeaders("*"); // Cabeçalhos permitidos
    }
}
