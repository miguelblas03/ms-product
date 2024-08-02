package com.company.msproject.service.impl;

import com.company.msproject.entity.Category;
import com.company.msproject.enums.StatusEnum;
import com.company.msproject.exception.NotFoundException;
import com.company.msproject.repository.CategoryRepository;
import com.company.msproject.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private static final String CATEGORY_NOT_FOUND_MESSAGE = "Category not found";

    @Override
    public Page<Category> getAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return categoryRepository.findAll(pageRequest);
    }

    @Override
    public Category create(Category category) {
        category.setId(null);
        return categoryRepository.save(category);
    }

    @Override
    public Category getById(Long id) throws NotFoundException {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(CATEGORY_NOT_FOUND_MESSAGE));
    }

    @Override
    public Category update(Long id, Category category) throws NotFoundException {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException(CATEGORY_NOT_FOUND_MESSAGE);
        }
        category.setId(id);
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) throws NotFoundException {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(CATEGORY_NOT_FOUND_MESSAGE));
        category.setStatus(StatusEnum.INACTIVE);
        categoryRepository.save(category);
    }
}
