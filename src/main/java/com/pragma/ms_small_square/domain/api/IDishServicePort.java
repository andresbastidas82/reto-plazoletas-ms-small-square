package com.pragma.ms_small_square.domain.api;

import com.pragma.ms_small_square.domain.model.Dish;
import com.pragma.ms_small_square.domain.model.Restaurant;
import com.pragma.ms_small_square.domain.model.enums.DishCategoryEnum;
import org.springframework.data.domain.Page;

public interface IDishServicePort {

    Dish saveDish(Dish dish, Restaurant restaurant);
    Dish updateDish(Dish currentDish, Dish dishUpdate);
    Dish updateStateDish(Dish dish, Boolean state);
    Dish getDishById(Long id);
    Page<Dish> getDishesByRestaurant(Long restaurantId, DishCategoryEnum category, int page, int size);
}
