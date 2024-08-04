package com.company.msproject.controller;

import com.company.msproject.dto.ProductCreateRequestDto;
import com.company.msproject.dto.ProductUpdateRequestDto;
import com.company.msproject.dto.ProductResponseDto;
import com.company.msproject.dto.ProductStatusRequestDto;
import com.company.msproject.entity.Category;
import com.company.msproject.entity.Product;
import com.company.msproject.enums.StatusEnum;
import com.company.msproject.exception.CategoryNotFoundException;
import com.company.msproject.exception.ProductNotFoundException;
import com.company.msproject.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.company.msproject.utils.EntityUtils.convertValue;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Page<ProductResponseDto> getAllProducts(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return productService.getAll(pageNumber, pageSize).map(product -> convertValue(product, ProductResponseDto.class));
    }

    @GetMapping("/{id}")
    public ProductResponseDto getProductById(@PathVariable Long id) throws ProductNotFoundException {
        return convertValue(productService.getById(id), ProductResponseDto.class);
    }

    @GetMapping("/search")
    public Page<ProductResponseDto> getProductByName(@RequestParam String name, @RequestParam int pageNumber, @RequestParam int pageSize) {
        return productService.findByName(name, pageNumber, pageSize).map(product -> convertValue(product, ProductResponseDto.class));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto createProduct(@Valid @RequestBody ProductCreateRequestDto productCreateRequestDto) throws CategoryNotFoundException {
        Product newProduct = convertValue(productCreateRequestDto, Product.class);
        addCategoryToProduct(newProduct, productCreateRequestDto.getCategoryId());
        return convertValue(productService.create(newProduct), ProductResponseDto.class);
    }

    @PutMapping("/{id}")
    public ProductResponseDto updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequestDto productUpdateRequestDto) throws ProductNotFoundException, CategoryNotFoundException {
        Product newProduct = convertValue(productUpdateRequestDto, Product.class);
        addCategoryToProduct(newProduct, productUpdateRequestDto.getCategoryId());
        return convertValue(productService.update(id, newProduct), ProductResponseDto.class);
    }

    @PatchMapping("/{id}")
    public ProductResponseDto updateProductStatus(@PathVariable Long id, @Valid @RequestBody ProductStatusRequestDto productStatusRequestDto) throws ProductNotFoundException {
        return convertValue(productService.updateStatus(id, StatusEnum.valueOf(productStatusRequestDto.getStatus())), ProductResponseDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        productService.deleteById(id);
    }

    private void addCategoryToProduct(Product product, Long categoryId) {
        Category category = new Category();
        category.setId(categoryId);
        product.setCategory(category);
    }
}
