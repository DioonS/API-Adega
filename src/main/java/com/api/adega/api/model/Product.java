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

    @NotBlank
    private String productName;

    private String productDescription;

    private Double price;

    @NotBlank
    private String contentType;

    @ManyToOne
    private Category category;
}
