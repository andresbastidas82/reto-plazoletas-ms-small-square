package com.pragma.ms_small_square.domain.model;

import com.pragma.ms_small_square.domain.model.enums.DishCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Dish {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String urlImage;
    private DishCategoryEnum category;
    private Restaurant restaurant;
    private Long ownerId;
    private Boolean state;
}
