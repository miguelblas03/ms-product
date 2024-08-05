package com.company.msproject.dto;

import com.company.msproject.enums.StatusEnum;
import com.company.msproject.validator.annotation.ValidEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductUpdateRequestDto {

    @NotBlank
    @Size(min = 3, max = 250)
    private String name;

    @Size(max = 250)
    private String description;

    @NotNull
    @Min(1)
    private Long categoryId;

    @NotBlank
    @ValidEnum(enumClass = StatusEnum.class)
    private String status;
}
