package com.pragma.ms_small_square.domain.api;

import com.pragma.ms_small_square.domain.model.Dish;

public interface IDishServicePort {

    Dish saveDish(Dish dish);

    Dish getDishById(Long id);
}
