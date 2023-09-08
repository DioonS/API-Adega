package com.api.adega.api.implementation;

import com.api.adega.api.exception.ProductException;
import com.api.adega.api.model.Category;
import com.api.adega.api.model.Product;
import com.api.adega.api.repository.CategoryRepo;
import com.api.adega.api.repository.ProductRepo;
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
    public List<Product> viewAllProduct() throws ProductException {
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
        Optional<Product> optional = pRepo.findById(product.getProductId());
        if (optional.isPresent()) {
            return pRepo.save(product);
        } else {
            throw new ProductException("Produto não atualizado");
        }
    }

    @Override
    public Product viewProduct(Integer productId) throws ProductException {
        Optional<Product> optional = pRepo.findById(productId);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ProductException("Produto não encontrado com o ID do Produto - " + productId);
        }
    }

    @Override
    public List<Product> viewProductByCategory(Integer categoryId) throws ProductException {
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
}
