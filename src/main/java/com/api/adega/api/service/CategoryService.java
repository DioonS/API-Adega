package com.api.adega.api.service;


import com.api.adega.api.entities.Category;
import com.api.adega.api.exception.CategoryException;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category) throws CategoryException;

    List<Category> getAllCategories() throws CategoryException;

    Category getCategoryById(Integer categoryId) throws CategoryException;

    Category updateCategory(Integer categoryId, Category updateCategory) throws CategoryException;

    void deleteCategory(Integer categoryId) throws CategoryException;
}
