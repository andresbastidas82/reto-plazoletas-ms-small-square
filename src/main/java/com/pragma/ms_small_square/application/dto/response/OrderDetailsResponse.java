package com.pragma.ms_small_square.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pragma.ms_small_square.domain.model.enums.DishCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderDetailsResponse {

    private Long dishId;
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private DishCategoryEnum category;
    private String description;
}
