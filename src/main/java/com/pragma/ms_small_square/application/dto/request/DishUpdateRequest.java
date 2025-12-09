package com.pragma.ms_small_square.application.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishUpdateRequest {

    @NotBlank(message = "The description is required")
    private String description;

    @NotNull(message = "The price is required")
    @Positive(message = "The price must be a positive number")
    @Digits(integer = 10, fraction = 0, message = "The price must be an integer without decimals")
    private BigDecimal price;

}
