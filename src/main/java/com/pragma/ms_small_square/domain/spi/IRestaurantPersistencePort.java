package com.pragma.ms_small_square.domain.spi;

import com.pragma.ms_small_square.domain.model.Restaurant;
import org.springframework.data.domain.Page;

public interface IRestaurantPersistencePort {

    Restaurant saveRestaurant(Restaurant restaurant);

    Restaurant getRestaurantById(Long id);

    Page<Restaurant> getRestaurants(Integer page, Integer size);

}
