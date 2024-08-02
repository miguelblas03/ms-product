package com.company.msproject.service.impl;

import com.company.msproject.entity.Category;
import com.company.msproject.entity.Product;
import com.company.msproject.enums.StatusEnum;
import com.company.msproject.exception.NotFoundException;
import com.company.msproject.repository.CategoryRepository;
import com.company.msproject.repository.ProductRepository;
import com.company.msproject.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";

    @Override
    public Page<Product> getAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return productRepository.findAll(pageRequest);
    }

    @Override
    public Product create(Product product) throws NotFoundException {
        product.setCategory(findCategoryById(product.getCategory().getId()));
        product.setId(null);
        return productRepository.save(product);
    }

    @Override
    public Product getById(Long id) throws NotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Override
    public Product update(Long id, Product product) throws NotFoundException {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }
        product.setId(id);
        product.setCategory(findCategoryById(product.getCategory().getId()));
        return productRepository.save(product);
    }

    @Override
    public Product updateStatus(Long id, StatusEnum status) throws NotFoundException {
        Product productToUpdate = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND_MESSAGE));
        productToUpdate.setStatus(status);
        return productRepository.save(productToUpdate);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> findByName(String name, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return productRepository.findByNameContainingIgnoreCaseAndStatusAndCategoryStatus(pageRequest, name, StatusEnum.ACTIVE, StatusEnum.ACTIVE);
    }

    private Category findCategoryById(Long id) throws NotFoundException {
        return categoryRepository.findByIdAndStatus(id, StatusEnum.ACTIVE)
                .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND_MESSAGE));
    }
}
