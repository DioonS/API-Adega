package com.api.adega.api.service.impl;

import com.api.adega.api.dto.ImageDto;
import com.api.adega.api.entities.Image;
import com.api.adega.api.exception.CategoryNotFoundException;
import com.api.adega.api.exception.ProductException;
import com.api.adega.api.entities.Category;
import com.api.adega.api.entities.Product;
import com.api.adega.api.repository.CategoryRepo;
import com.api.adega.api.repository.ProductRepo;
import com.api.adega.api.service.CategoryService;
import com.api.adega.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo pRepo;

    @Autowired
    private CategoryRepo cRepo;

    @Override
    public List<Product> getAllProducts() throws ProductException {
        List<Product> products = pRepo.findAll();
        if (!products.isEmpty()) {
            return products;
        } else {
            throw new ProductException("Produtos não encontrados");
        }
    }

    @Override
    public Product addProduct(Product product) throws ProductException {
        Product prod = pRepo.save(product);
        if (prod != null) {
            return prod;
        } else {
            throw new ProductException("Produto não adicionado!");
        }
    }

    @Override
    public Product updateProduct(Product product) throws ProductException {
        // Checa se o ID do produto existe antes de fazer a atualização
        Integer productId = product.getProductId();
        Optional<Product> optional = pRepo.findById(productId);
        if (optional.isPresent()) {
            Product existingProduct = optional.get();

            // Atualiza apenas campos permitidos
            existingProduct.setProductName(product.getProductName());
            existingProduct.setProductDescription(product.getProductDescription());
            existingProduct.setPrice(product.getPrice());

            // Salva e retorna o produto atualizado
            return pRepo.save(product);
        } else {
            throw new ProductException("Produto não atualizado");
        }
    }

    @Override
    public Product getProductById(Integer productId) throws ProductException {

        System.out.println("imagem");

        Optional<Product> optional = pRepo.findById(productId);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ProductException("Produto não encontrado com o ID do Produto - " + productId);
        }
    }

    @Override
    public Category getCategoryById(Integer categoryId) throws ProductException {
        Optional<Category> category = cRepo.findById(categoryId);
        if (category.isPresent()) {
            return category.get();
        } else {
            throw new ProductException("Categoria não encontrada com o ID da Categoria - " + categoryId);
        }
    }

    @Override
    public List<Product> getProductByCategory(Integer categoryId) throws ProductException {
        Optional<Category> category = cRepo.findById(categoryId);
        if (category.isPresent()) {
            return category.get().getProductList();
        } else {
            throw new ProductException("Produto não encontrado com o ID da Categoria - " + categoryId);
        }
    }

    @Override
    public Product removeProduct(Integer productId) throws ProductException {
        Product product = pRepo.findById(productId).orElseThrow(() -> new ProductException("Produto não encontrado"));
        pRepo.delete(product);
        return product;
    }

    @Override
    public Category setCategoryById(Integer categoryId) throws CategoryNotFoundException {
        Optional<Category> categoryOptional = cRepo.findById(categoryId);
        if (categoryOptional.isPresent()) {
            return categoryOptional.get();
        } else {
            throw new CategoryNotFoundException("Categoria não encontrada com o ID: " + categoryId);
        }
    }
}
