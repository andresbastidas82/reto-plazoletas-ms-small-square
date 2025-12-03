package com.pragma.ms_small_square.application.handler;

import com.pragma.ms_small_square.application.dto.RestaurantRequest;
import com.pragma.ms_small_square.application.dto.RestaurantResponse;

public interface IRestaurantHandler {

    RestaurantResponse saveRestaurant(RestaurantRequest restaurantRequest);
}
