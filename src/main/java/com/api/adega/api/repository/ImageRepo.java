package com.api.adega.api.repository;

import com.api.adega.api.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepo extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String name);
    void deleteByName(String name);

}
