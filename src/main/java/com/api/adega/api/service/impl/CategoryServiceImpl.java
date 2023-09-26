package com.api.adega.api.service.impl;

import com.api.adega.api.entities.Category;
import com.api.adega.api.exception.CategoryNotFoundException;
import com.api.adega.api.repository.CategoryRepo;
import com.api.adega.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        Optional<Category> categoryOptional = categoryRepo.findById(categoryId);
        return categoryOptional.orElse(null);
    }

    @Override
    public Category updateCategory(Integer categoryId, Category updateCategory) {
        Optional<Category> categoryOptional = categoryRepo.findById(categoryId);

        if (categoryOptional.isPresent()) {
            Category existingCategory = categoryOptional.get();
            existingCategory.setCategoryName(updateCategory.getCategoryName());
            return categoryRepo.save(existingCategory);
        } else {
            return null;
        }
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        categoryRepo.deleteById(categoryId);
    }

    @Override
    public Category setCategoryById(Integer categoryId) throws CategoryNotFoundException {
        Optional<Category> categoryOptional = categoryRepo.findById(categoryId);
        if (categoryOptional.isPresent()) {
            return categoryOptional.get();
        } else {
            throw new CategoryNotFoundException("Categoria não encontrada com o ID: " + categoryId);
        }
    }

}
