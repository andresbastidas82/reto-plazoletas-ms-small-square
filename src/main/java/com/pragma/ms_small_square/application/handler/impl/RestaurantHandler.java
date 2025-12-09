package com.pragma.ms_small_square.application.handler.impl;

import com.pragma.ms_small_square.application.dto.request.RestaurantRequest;
import com.pragma.ms_small_square.application.dto.response.RestaurantResponse;
import com.pragma.ms_small_square.application.handler.IRestaurantHandler;
import com.pragma.ms_small_square.application.mapper.RestaurantRequestMapper;
import com.pragma.ms_small_square.domain.api.IRestaurantServicePort;
import com.pragma.ms_small_square.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandler implements IRestaurantHandler {

    private final RestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantServicePort restaurantServicePort;

    @Override
    public RestaurantResponse saveRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantServicePort.saveRestaurant(restaurantRequestMapper.toRestaurant(restaurantRequest));
        return restaurantRequestMapper.toRestaurantResponse(restaurant);
    }

    @Override
    public Page<RestaurantResponse> getRestaurants(int page, int size) {
        Page<Restaurant> restaurants = restaurantServicePort.getRestaurants(page, size);
        return restaurants.map(restaurantRequestMapper::toRestaurantResponse);
    }
}
