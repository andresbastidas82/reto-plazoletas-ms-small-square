package com.pragma.ms_small_square.domain.spi;

import com.pragma.ms_small_square.domain.model.Restaurant;

public interface IRestaurantPersistencePort {

    Restaurant saveRestaurant(Restaurant restaurant);

}
