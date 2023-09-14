package com.api.adega.api.controller;

import com.api.adega.api.exception.CategoryNotFoundException;
import com.api.adega.api.exception.ProductNotFoundException;
import com.api.adega.api.model.Category;
import com.api.adega.api.model.Image;
import com.api.adega.api.model.Product;
import com.api.adega.api.repository.ProductRepo;
import com.api.adega.api.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepo productRepo;

    @GetMapping//("/view")
    @Operation(summary = "Lista todos os produtos do sistema.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Lista gerada com sucesso!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Image.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content)
    })
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping//("/create")
    @Operation(summary = "Adiciona novo produto no sistema.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Produto adicionado com sucesso!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Image.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content)
    })
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
        try {
            Category category = productService.getCategoryById(product.getCategory().getCategoryId());
            product.setCategory(category);

            Product prod = productService.addProduct(product);
            return new ResponseEntity<>(prod, HttpStatus.CREATED);
        } catch (CategoryNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{productsId}")
    @Operation(summary = "Atualiza produto no sistema.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Image.class)) }),
            @ApiResponse(responseCode = "404", description = "Não encontrado",
                    content = @Content)
    })
    public ResponseEntity<Product> updateProduct(@PathVariable("productsId") Integer productsId, @Valid @RequestBody Product product)  {
        try {
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
        }
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Pesquisa produto no sistema por ID.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Produto pesquisado com sucesso!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Image.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content)
    })
    public ResponseEntity<Product> getProductById(@PathVariable("productId") @Min(1) Integer productId) {
        Product product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Lista produto por Categoria.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Listagem gerada com sucesso!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Image.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content)
    })
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable("categoryId") @Min(1) Integer categoryId) {
        List<Product> products = productService.getProductByCategory(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/{productsId}")
    @Operation(summary = "Exclui produto no sistema.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "Produto excluido com sucesso.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Image.class)) }),
            @ApiResponse(responseCode = "404", description = "Não encontrado",
                    content = @Content)
    })
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
