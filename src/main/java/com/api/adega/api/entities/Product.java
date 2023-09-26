package com.api.adega.api.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
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

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Image images;
}
