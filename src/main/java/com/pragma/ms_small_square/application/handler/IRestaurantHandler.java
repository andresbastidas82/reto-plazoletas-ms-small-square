package com.pragma.ms_small_square.application.handler;

import com.pragma.ms_small_square.application.dto.request.RestaurantRequest;
import com.pragma.ms_small_square.application.dto.response.RestaurantResponse;
import org.springframework.data.domain.Page;

public interface IRestaurantHandler {

    RestaurantResponse saveRestaurant(RestaurantRequest restaurantRequest);
    Page<RestaurantResponse> getRestaurants(int page, int size);
}
