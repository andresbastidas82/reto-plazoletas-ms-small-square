package com.pragma.ms_small_square.domain.api;

import com.pragma.ms_small_square.domain.model.Restaurant;
import org.springframework.data.domain.Page;


public interface IRestaurantServicePort {

    Restaurant saveRestaurant(Restaurant restaurant);

    Restaurant getRestaurantById(Long id);

    Page<Restaurant> getRestaurants(Integer page, Integer size);


}
