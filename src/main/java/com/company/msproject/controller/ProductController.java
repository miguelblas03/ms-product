package com.company.msproject.controller;

import com.company.msproject.dto.ProductRequestDto;
import com.company.msproject.dto.ProductResponseDto;
import com.company.msproject.dto.ProductStatusRequestDto;
import com.company.msproject.entity.Category;
import com.company.msproject.entity.Product;
import com.company.msproject.enums.StatusEnum;
import com.company.msproject.exception.NotFoundException;
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
    public ProductResponseDto getProductById(@PathVariable Long id) throws NotFoundException {
        return convertValue(productService.getById(id), ProductResponseDto.class);
    }

    @GetMapping("/search")
    public Page<ProductResponseDto> getProductByName(@RequestParam String name, @RequestParam int pageNumber, @RequestParam int pageSize) {
        return productService.findByName(name, pageNumber, pageSize).map(product -> convertValue(product, ProductResponseDto.class));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto createProduct(@Valid @RequestBody ProductRequestDto productRequestDto) throws NotFoundException {
        Product newProduct = buildProductWithCategory(productRequestDto);
        return convertValue(productService.create(newProduct), ProductResponseDto.class);
    }

    @PutMapping("/{id}")
    public ProductResponseDto updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDto productRequestDto) throws NotFoundException {
        Product newProduct = buildProductWithCategory(productRequestDto);
        return convertValue(productService.update(id, newProduct), ProductResponseDto.class);
    }

    @PatchMapping("/{id}")
    public ProductResponseDto updateProductStatus(@PathVariable Long id, @Valid @RequestBody ProductStatusRequestDto productStatusRequestDto) throws NotFoundException {
        return convertValue(productService.updateStatus(id, StatusEnum.valueOf(productStatusRequestDto.getStatus())), ProductResponseDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }

    private Product buildProductWithCategory(ProductRequestDto productRequestDto) {
        Product newProduct = convertValue(productRequestDto, Product.class);
        Category category = new Category();
        category.setId(productRequestDto.getCategoryId());
        newProduct.setCategory(category);
        return newProduct;
    }
}
