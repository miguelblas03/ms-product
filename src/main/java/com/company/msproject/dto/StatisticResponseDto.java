package com.company.msproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticResponseDto {
    private Long id;
    private CategoryResponseDto category;
    private Long totalProducts;
    private String description;
}
