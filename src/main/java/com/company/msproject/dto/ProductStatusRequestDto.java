package com.company.msproject.dto;

import com.company.msproject.enums.StatusEnum;
import com.company.msproject.validator.annotation.ValidEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductStatusRequestDto {

    @NotNull
    @ValidEnum(enumClass = StatusEnum.class)
    private String status;
}
