package com.company.msproject.service;

import com.company.msproject.entity.Product;
import com.company.msproject.enums.StatusEnum;
import com.company.msproject.exception.NotFoundException;
import org.springframework.data.domain.Page;

public interface ProductService {

    Page<Product> getAll(int pageNumber, int pageSize);

    Product create(Product product) throws NotFoundException;

    Product getById(Long id) throws NotFoundException;

    Product update(Long id, Product product) throws NotFoundException;

    Product updateStatus(Long id, StatusEnum status) throws NotFoundException;

    void deleteById(Long id);

    Page<Product> findByName(String name, int pageNumber, int pageSize);
}
