package com.api.adega.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer categoryId;

    private String categoryName;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Product> productList;
}
