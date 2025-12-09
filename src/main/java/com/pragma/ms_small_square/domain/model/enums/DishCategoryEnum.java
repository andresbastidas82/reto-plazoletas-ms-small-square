package com.pragma.ms_small_square.domain.model.enums;

import com.pragma.ms_small_square.infrastructure.exception.DishCategoryNotFounException;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
public enum DishCategoryEnum {

    APPETIZER("Entrada"),
    MAIN_COURSE("Plato Fuerte"),
    SIDE_DISH("Acompañamiento"),
    DESSERT("Postre"),
    DRINK("Bebida"),
    VEGETARIAN("Vegetariano"),
    VEGAN("Vegano"),
    GLUTEN_FREE("Sin Gluten"),
    SPICY("Picante"),
    KIDS("Infantil"),
    PROMOTION("Promoción");

    private final String name;

    DishCategoryEnum(String name) {
        this.name = name;
    }

    public static String getCategoriesAsString() {
        return Arrays.stream(DishCategoryEnum.values())
                .map(item -> item.name() + " (" + item.getName() + ")")
                .collect(Collectors.joining(", "));
    }

    public static DishCategoryEnum getDishCategory(String name) {
        try{
            return DishCategoryEnum.valueOf(name);
        } catch (Exception e){
            throw new DishCategoryNotFounException();
        }
    }

}
