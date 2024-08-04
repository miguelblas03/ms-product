package com.company.msproject.service.impl;

import com.company.msproject.entity.Category;
import com.company.msproject.enums.StatusEnum;
import com.company.msproject.exception.CategoryNotFoundException;
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
    public Category getById(Long id) throws CategoryNotFoundException {
        return categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
    }

    @Override
    public Category update(Long id, Category category) throws CategoryNotFoundException {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException();
        }
        category.setId(id);
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) throws CategoryNotFoundException {
        Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        category.setStatus(StatusEnum.INACTIVE);
        categoryRepository.save(category);
    }
}
