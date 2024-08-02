package com.company.msproject.repository;

import com.company.msproject.entity.Product;
import com.company.msproject.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContainingIgnoreCaseAndStatusAndCategoryStatus(PageRequest pageRequest, String name, StatusEnum productStatus, StatusEnum categoryStatus);
}
