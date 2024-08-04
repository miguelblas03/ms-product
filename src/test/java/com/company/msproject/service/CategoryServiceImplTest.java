package com.company.msproject.service;

import com.company.msproject.entity.Category;
import com.company.msproject.enums.StatusEnum;
import com.company.msproject.exception.CategoryNotFoundException;
import com.company.msproject.repository.CategoryRepository;
import com.company.msproject.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {

    private final CategoryRepository categoryRepository = mock(CategoryRepository.class);

    private final CategoryServiceImpl categoryService = new CategoryServiceImpl(categoryRepository);

    private List<Category> categories;
    private Category category;

    @BeforeEach
    void setUp() {
        categories = new ArrayList<>();
        category = new Category(1L, "Category A", "Category A", StatusEnum.ACTIVE);
        categories.add(category);
    }

    @Test
    void getAll() {
        Page<Category> categoryPage = new PageImpl<>(categories);
        when(categoryRepository.findAll(any(PageRequest.class))).thenReturn(categoryPage);

        var categories = categoryService.getAll(0, 10);
        assertTrue(categories.getTotalElements() > 0);
    }

    @Test
    void create() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        var newCategory = categoryService.create(category);

        assertEquals(category.getId(), newCategory.getId());
    }

    @Test
    void getById_ok() throws CategoryNotFoundException {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(category));
        var categoryDb = categoryService.getById(1L);

        assertEquals(categoryDb.getId(), category.getId());
    }

    @Test
    void getById_fail_category_not_found() {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> categoryService.getById(1L));
    }

    @Test
    void update_ok() throws CategoryNotFoundException {
        when(categoryRepository.existsById(any(Long.class))).thenReturn(true);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        var newCategory = categoryService.update(1L, category);

        assertEquals(category.getId(), newCategory.getId());
    }

    @Test
    void update_fail_category_not_found() {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> categoryService.update(1L, category));
    }

    @Test
    void deleteById_ok() throws CategoryNotFoundException {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(category));
        categoryService.deleteById(1L);

        Mockito.verify(categoryRepository, Mockito.times(1)).save(category);
    }

    @Test
    void deleteById_fail_category_not_found() {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteById(1L));
    }
}
