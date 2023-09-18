package com.api.adega.api.repository;

import com.api.adega.api.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String name);
    void deleteByName(String name);

}
