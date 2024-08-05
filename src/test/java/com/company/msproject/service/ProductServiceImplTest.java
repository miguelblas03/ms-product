package com.company.msproject.service;

import com.company.msproject.entity.Category;
import com.company.msproject.entity.Product;
import com.company.msproject.enums.StatusEnum;
import com.company.msproject.exception.CategoryNotFoundException;
import com.company.msproject.exception.ProductNotFoundException;
import com.company.msproject.repository.CategoryRepository;
import com.company.msproject.repository.ProductRepository;
import com.company.msproject.service.impl.ProductServiceImpl;
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

class ProductServiceImplTest {

    private final ProductRepository productRepository = mock(ProductRepository.class);

    private final CategoryRepository categoryRepository = mock(CategoryRepository.class);

    private final ProduceEventService produceEventService = mock(ProduceEventService.class);

    private final ProductServiceImpl productService = new ProductServiceImpl(productRepository, categoryRepository, produceEventService);

    private List<Product> products;
    private Category category;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();
        category = new Category(1L, "Category A", "Category A", StatusEnum.ACTIVE);
        product1 = new Product(1L, "Product A", "Product A", category, StatusEnum.ACTIVE);
        product2 = new Product(2L, "Product B", "Product B", category, StatusEnum.ACTIVE);
        products.add(product1);
        products.add(product2);
    }

    @Test
    void getAll() {
        Page<Product> productPage = new PageImpl<>(products);
        when(productRepository.findAll(any(PageRequest.class))).thenReturn(productPage);
        var products = productService.getAll(0, 10);

        assertTrue(products.getTotalElements() > 0);
    }

    @Test
    void create_ok() throws CategoryNotFoundException {
        when(categoryRepository.findByIdAndStatus(1L, StatusEnum.ACTIVE)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product1);
        var newProduct = productService.create(product1);

        assertEquals(product1.getCategory().getId(), newProduct.getCategory().getId());
    }

    @Test
    void create_fail_category_not_found() {
        when(categoryRepository.findByIdAndStatus(1L, StatusEnum.ACTIVE)).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> productService.create(product1));
    }

    @Test
    void getById_ok() throws ProductNotFoundException {
        when(productRepository.findById(2L)).thenReturn(Optional.of(product2));
        var product = productService.getById(2L);

        assertEquals(product2.getId(), product.getId());
    }

    @Test
    void getById_fail_product_not_found() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getById(2L));
    }

    @Test
    void update_ok_category_not_changed() throws ProductNotFoundException, CategoryNotFoundException {
        when(categoryRepository.findByIdAndStatus(1L, StatusEnum.ACTIVE)).thenReturn(Optional.of(category));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productRepository.save(any(Product.class))).thenReturn(product1);
        var updatedProduct = productService.update(1L, product1);

        assertEquals(product1.getCategory().getId(), updatedProduct.getCategory().getId());
    }

    @Test
    void update_category_changed() throws ProductNotFoundException, CategoryNotFoundException {
        when(categoryRepository.findByIdAndStatus(1L, StatusEnum.ACTIVE)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product1);
        Category previousCategory = new Category(2L, "Category B", "Category B", StatusEnum.ACTIVE);
        Product productDb = new Product(1L, "Product A", "Product A", previousCategory, StatusEnum.ACTIVE);
        productDb.setCategory(previousCategory);
        when(productRepository.findById(1L)).thenReturn(Optional.of(productDb));
        var updatedProduct = productService.update(1L, product1);

        assertEquals(product1.getCategory().getId(), updatedProduct.getCategory().getId());
    }

    @Test
    void update_ok_product_is_inactive() throws ProductNotFoundException, CategoryNotFoundException {
        when(categoryRepository.findByIdAndStatus(1L, StatusEnum.ACTIVE)).thenReturn(Optional.of(category));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        product1.setStatus(StatusEnum.INACTIVE);
        when(productRepository.save(any(Product.class))).thenReturn(product1);
        var updatedProduct = productService.update(1L, product1);

        assertEquals(product1.getCategory().getId(), updatedProduct.getCategory().getId());
    }

    @Test
    void update_fail_product_not_found() {
        when(productRepository.existsById(1L)).thenReturn(false);
        assertThrows(ProductNotFoundException.class, () -> productService.update(1L, product1));
    }

    @Test
    void updateStatus_ok() throws ProductNotFoundException {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productRepository.save(any(Product.class))).thenReturn(product1);
        var updatedProduct = productService.updateStatus(1L, StatusEnum.ACTIVE);

        assertEquals(StatusEnum.ACTIVE, updatedProduct.getStatus());
    }

    @Test
    void update_status_ok_product_is_inactive() throws ProductNotFoundException {
        product1.setStatus(StatusEnum.INACTIVE);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productRepository.save(any(Product.class))).thenReturn(product1);
        var updatedProduct = productService.updateStatus(1L, StatusEnum.INACTIVE);

        assertEquals(StatusEnum.INACTIVE, updatedProduct.getStatus());
    }

    @Test
    void updateStatus_fail_product_not_found() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.updateStatus(1L, StatusEnum.INACTIVE));
    }

    @Test
    void deleteById() throws ProductNotFoundException {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        productService.deleteById(1L);
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void findByName() {
        Page<Product> productPage = new PageImpl<>(products);
        when(productRepository.findByNameContainingIgnoreCaseAndStatusAndCategoryStatus(any(), any(), any(), any())).thenReturn(productPage);
        var products = productService.findByName("Product A", 0, 10);

        assertTrue(products.getTotalElements() > 0);
    }
}
