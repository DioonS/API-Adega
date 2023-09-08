package com.api.adega.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Blob;

@Data
@Embeddable
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productId;

    private ImageSource imageSource; // Enum para indicar a origem da imagem (UPLOAD ou EXTERNAL_URL)

    @Lob
    private Blob productImage; // Campo Blob para armazenar a imagem (será preenchido somente se a origem for UPLOAD)

    private String productDescription;

    private Double price;

    private String contentType;

    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;
}
