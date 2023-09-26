package com.api.adega.api.dto;

import com.api.adega.api.entities.Category;
import com.api.adega.api.entities.Product;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String productName;
    private String productDescription;
    private Double productPrice;
    private String contentType;
    private Category category;
    private Integer categoryId;
    private Product product;
}
