package com.api.adega.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer imageId;

    //private ImageSource imageSource;

    private String productImageFileName; // Nome da imagem

    @Lob
    private byte[] productImage; // Campo para armazenar a imagem (será preenchido somente se a origem for UPLOAD)

}
