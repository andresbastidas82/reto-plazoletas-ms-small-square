package com.pragma.ms_small_square.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String restaurant;
    private String urlImage;
    private String category;
    private Long ownerId;
    private String state;
}
