package com.company.msproject.dto;

import com.company.msproject.enums.StatusEnum;
import com.company.msproject.validator.annotation.ValidEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryRequestDto {
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @Size(max = 250)
    private String description;

    @NotBlank
    @ValidEnum(enumClass = StatusEnum.class)
    private String status;
}
