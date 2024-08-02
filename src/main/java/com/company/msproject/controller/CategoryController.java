package com.company.msproject.controller;

import com.company.msproject.dto.CategoryRequestDto;
import com.company.msproject.dto.CategoryResponseDto;
import com.company.msproject.entity.Category;
import com.company.msproject.exception.NotFoundException;
import com.company.msproject.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.company.msproject.utils.EntityUtils.convertValue;

@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public Page<CategoryResponseDto> getAllCategories(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return categoryService.getAll(pageNumber, pageSize).map(category -> convertValue(category, CategoryResponseDto.class));
    }

    @GetMapping("/{id}")
    public CategoryResponseDto getCategoryById(@PathVariable Long id) throws NotFoundException {
        return convertValue(categoryService.getById(id), CategoryResponseDto.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto createCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        Category newCategory = convertValue(categoryRequestDto, Category.class);
        return convertValue(categoryService.create(newCategory), CategoryResponseDto.class);
    }

    @PutMapping("/{id}")
    public CategoryResponseDto updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequestDto categoryRequestDto) throws NotFoundException {
        Category newCategory = convertValue(categoryRequestDto, Category.class);
        return convertValue(categoryService.update(id, newCategory), CategoryResponseDto.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) throws NotFoundException {
        categoryService.deleteById(id);
    }
}
