package com.company.msproject.utils;

import com.company.msproject.dto.ProductRequestDto;
import com.company.msproject.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EntityUtilsTest {

    private ProductRequestDto productRequestDto;

    @BeforeEach
    void setUp() {
        productRequestDto = new ProductRequestDto("Product A", "Product A", 1L, "ACTIVE");
    }

    @Test
    void convertValue_ok() {
        var product = EntityUtils.convertValue(productRequestDto, Product.class);
        assertEquals("Product A", product.getName());
    }

    @Test
    void convertValue_fail() {
        assertThrows(Exception.class, () -> EntityUtils.convertValue(productRequestDto, null));
    }
}
