package com.pragma.ms_small_square.domain.spi;

import com.pragma.ms_small_square.domain.model.Dish;

public interface IDishPersistencePort {

    Dish saveDish(Dish dish);

}
