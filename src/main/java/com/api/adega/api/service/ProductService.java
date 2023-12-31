package com.api.adega.api.service;

import com.api.adega.api.exception.ProductException;
import com.api.adega.api.entities.Category;
import com.api.adega.api.entities.Product;

import java.util.List;

public interface ProductService {

    public List<Product> getAllProducts() throws ProductException;

    public Product addProduct(Product product) throws ProductException;

    public Product updateProduct(Product product) throws ProductException;

    public Product getProductById(Integer productId) throws ProductException;

    public Category getCategoryById(Integer categoryId) throws ProductException;

    public List<Product> getProductByCategory(Integer categoryId) throws ProductException;

    public Product removeProduct(Integer productId) throws ProductException;

    Category setCategoryById(Integer categoryId);
}
