package com.pragma.ms_small_square.domain.spi;

import com.pragma.ms_small_square.domain.model.Dish;
import com.pragma.ms_small_square.domain.model.enums.DishCategoryEnum;
import org.springframework.data.domain.Page;

public interface IDishPersistencePort {

    Dish saveDish(Dish dish);

    Dish getDishById(Long id);

    Page<Dish> getDishesByRestaurant(Long restaurantId, DishCategoryEnum category, int page, int size);

}
