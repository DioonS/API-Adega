package com.api.adega.api.controller;

import com.api.adega.api.entities.Category;
import com.api.adega.api.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Cria nova categoria.", description = "Insere uma nova categoria na lista de categorias.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Adicionado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @Operation(summary = "Lista categorias.", description = "Retorna lista de categorias presentes no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista gerada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @Operation(summary = "Atualiza categoria.", description = "Atualiza categorias via ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{categoryId}")
    public Category updateCategory(@PathVariable Integer categoryId, @RequestBody Category updateCategory) {
        return categoryService.updateCategory(categoryId, updateCategory);
    }

    @Operation(summary = "Exclui categoria.", description = "Exclui categoria tendo o ID por referência.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Excluida com sucesso"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
