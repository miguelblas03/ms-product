package com.company.msproject.service;

import com.company.msproject.entity.Category;
import com.company.msproject.exception.CategoryNotFoundException;
import org.springframework.data.domain.Page;

public interface CategoryService {

    Page<Category> getAll(int pageNumber, int pageSize);

    Category create(Category category);

    Category getById(Long id) throws CategoryNotFoundException;

    Category update(Long id, Category category) throws CategoryNotFoundException;

    void deleteById(Long id) throws CategoryNotFoundException;
}
