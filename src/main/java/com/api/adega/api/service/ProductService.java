package com.api.adega.api.service;

import com.api.adega.api.exception.ProductException;
import com.api.adega.api.model.Product;

import java.util.List;

public interface ProductService {

    public List<Product> viewAllProduct() throws ProductException;

    public Product addProduct(Product product) throws ProductException;

    public Product updateProduct(Product product) throws ProductException;

    public Product viewProduct(Integer productId) throws ProductException;

    public List<Product> viewProductByCategory(Integer categoryId) throws ProductException;

    public Product removeProduct(Integer productId) throws ProductException;
}
