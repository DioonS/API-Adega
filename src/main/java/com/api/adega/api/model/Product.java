package com.api.adega.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Embeddable
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productId;

    private ImageSource imageSource; // Enum para indicar a origem da imagem (UPLOAD ou EXTERNAL_URL)


    @Column
    private String productImageFileName; // Nome da imagem

    private String productImage; // Campo para armazenar a imagem (será preenchido somente se a origem for UPLOAD)

    @NotBlank
    private String productName;

    private String productDescription;

    private Double price;

    @NotBlank
    private String contentType;

    @ManyToOne
    private Category category;
}
