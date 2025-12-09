package com.pragma.ms_small_square.domain.api;

import com.pragma.ms_small_square.domain.model.Dish;
import com.pragma.ms_small_square.domain.model.Restaurant;

public interface IDishServicePort {

    Dish saveDish(Dish dish, Restaurant restaurant);
    Dish updateDish(Dish currentDish, Dish dishUpdate);
    Dish updateStateDish(Dish dish, Boolean state);
    Dish getDishById(Long id);
}
