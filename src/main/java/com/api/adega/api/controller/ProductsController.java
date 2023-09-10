package com.api.adega.api.controller;

import com.api.adega.api.exception.ImageUploadException;
import com.api.adega.api.exception.ProductException;
import com.api.adega.api.exception.ProductNotFoundException;
import com.api.adega.api.model.Category;
import com.api.adega.api.model.ImageSource;
import com.api.adega.api.model.Product;
import com.api.adega.api.repository.ProductRepo;
import com.api.adega.api.service.ImageService;
import com.api.adega.api.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductRepo productRepo;

    @GetMapping//("/view")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping//("/create")
    public ResponseEntity<Product> addProduct(@Valid Product product, @RequestParam("imageFile") MultipartFile imageFile) throws ProductException {
        try {
            // Upload da imagem e recuperar o arquivo pelo nome
            String imageFileName = imageService.uploadImage(imageFile);
            product.setProductImageFileName(imageFileName);

            if (product.getImageSource() == ImageSource.UPLOAD) {
                byte[] imageBytes = imageFile.getBytes();
                product.setProductImage(imageBytes);
            }

            // salvar o produto
            Product prod = productRepo.save(product);

            if (prod != null) {
                return new ResponseEntity<>(prod, HttpStatus.OK);
            } else {
                throw new ProductException("Produto não adicionado!");
            }
        } catch (ImageUploadException e) {
            throw new ProductException("Falha no upload da imagem: " + e.getMessage());
        } catch (IOException e) {
            throw new ProductException("Falha no processamento da imagem: " + e.getMessage());
        }
    }

    @PutMapping("/{productsId}")
    public ResponseEntity<Product> updateProduct(@PathVariable("productsId") Integer productsId, @Valid @RequestBody Product product, MultipartFile imageFile)  {
        try {

            if (imageFile != null && product.getImageSource() == ImageSource.UPLOAD) {
                String imageFileName = imageService.uploadImage(imageFile);
                product.setProductImageFileName(imageFileName);
                byte[] imageBytes = imageFile.getBytes();
                product.setProductImage(imageBytes);
            }

            Product existingProduct = productService.getProductById(productsId);

            existingProduct.setProductName(product.getProductName());
            existingProduct.setProductDescription(product.getProductDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setContentType(product.getContentType());
            existingProduct.setCategory(product.getCategory());

            Category category = productService.getCategoryById(product.getCategory().getCategoryId());
            existingProduct.setCategory(category);

            Product updateProd = productService.updateProduct(existingProduct);
            return new ResponseEntity<>(updateProd, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            throw new ProductException("Falha no processamento da imagem: " + e.getMessage());
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") @Min(1) Integer productId) {
        Product product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable("categoryId") @Min(1) Integer categoryId) {
        List<Product> products = productService.getProductByCategory(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/{productsId}")
    public ResponseEntity<Product> removeProductById(@PathVariable("productsId") Integer productsId) {
        try {
            Product product = productService.getProductById(productsId);

            product.setCategory(null);

            productService.updateProduct(product);

            productService.removeProduct(productsId);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}