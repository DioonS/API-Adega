package com.api.adega.api.dto;

import com.api.adega.api.entities.Image;
import com.api.adega.api.entities.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductWithImageDto {

    private Product product;

    @JsonIgnore
    private Image image;
}
