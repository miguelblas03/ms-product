package com.company.msproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private String status;
    private CategoryRequestDto category;
}
