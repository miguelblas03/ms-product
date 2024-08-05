package com.company.msproject.service.impl;

import com.company.commoneventlib.enums.ActionEnum;
import com.company.msproject.entity.Category;
import com.company.msproject.entity.Product;
import com.company.msproject.enums.StatusEnum;
import com.company.msproject.exception.CategoryNotFoundException;
import com.company.msproject.exception.ProductNotFoundException;
import com.company.msproject.repository.CategoryRepository;
import com.company.msproject.repository.ProductRepository;
import com.company.msproject.service.ProduceEventService;
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

    private final ProduceEventService produceEventService;

    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";

    @Override
    public Page<Product> getAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return productRepository.findAll(pageRequest);
    }

    @Override
    public Product create(Product product) throws CategoryNotFoundException {
        product.setCategory(findCategoryById(product.getCategory().getId()));
        product.setId(null);
        product.setStatus(StatusEnum.ACTIVE);

        Product newProduct = productRepository.save(product);
        produceEventService.buildAndSendEvent(newProduct, product.getCategory().getId(), ActionEnum.CREATE_PRODUCT);
        return newProduct;
    }

    @Override
    public Product getById(Long id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public Product update(Long id, Product product) throws ProductNotFoundException, CategoryNotFoundException {
        Product productToUpdate = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        boolean sendEvent = false;
        Long previousCategoryId = productToUpdate.getCategory().getId();
        if (!product.getCategory().getId().equals(previousCategoryId)) {
            sendEvent = true;
        }

        productToUpdate.setCategory(findCategoryById(product.getCategory().getId()));
        Product updatedProduct = productRepository.save(productToUpdate);

        if (sendEvent) {
            produceEventService.buildAndSendEvent(updatedProduct, previousCategoryId, ActionEnum.CHANGE_CATEGORY);
        }

        return updatedProduct;
    }

    @Override
    public Product updateStatus(Long id, StatusEnum status) throws ProductNotFoundException {
        Product productToUpdate = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        productToUpdate.setStatus(status);
        return productRepository.save(productToUpdate);
    }

    @Override
    public void deleteById(Long id) throws ProductNotFoundException {
        Product productToDelete = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        produceEventService.buildAndSendEvent(productToDelete, productToDelete.getCategory().getId(), ActionEnum.DELETE_PRODUCT);

        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> findByName(String name, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return productRepository.findByNameContainingIgnoreCaseAndStatusAndCategoryStatus(pageRequest, name, StatusEnum.ACTIVE, StatusEnum.ACTIVE);
    }

    private Category findCategoryById(Long id) throws CategoryNotFoundException {
        return categoryRepository.findByIdAndStatus(id, StatusEnum.ACTIVE)
                .orElseThrow(CategoryNotFoundException::new);
    }
}
