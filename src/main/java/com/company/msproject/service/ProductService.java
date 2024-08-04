package com.company.msproject.service;

import com.company.msproject.entity.Product;
import com.company.msproject.enums.StatusEnum;
import com.company.msproject.exception.CategoryNotFoundException;
import com.company.msproject.exception.ProductNotFoundException;
import org.springframework.data.domain.Page;

public interface ProductService {

    Page<Product> getAll(int pageNumber, int pageSize);

    Product create(Product product) throws CategoryNotFoundException;

    Product getById(Long id) throws ProductNotFoundException;

    Product update(Long id, Product product) throws ProductNotFoundException, CategoryNotFoundException;

    Product updateStatus(Long id, StatusEnum status) throws ProductNotFoundException;

    void deleteById(Long id) throws ProductNotFoundException;

    Page<Product> findByName(String name, int pageNumber, int pageSize);
}
