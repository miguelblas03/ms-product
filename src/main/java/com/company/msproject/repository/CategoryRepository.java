package com.company.msproject.repository;

import com.company.msproject.entity.Category;
import com.company.msproject.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByIdAndStatus(Long id, StatusEnum status);
}
