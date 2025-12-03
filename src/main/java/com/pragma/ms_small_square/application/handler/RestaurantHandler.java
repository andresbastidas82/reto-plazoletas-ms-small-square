package com.pragma.ms_small_square.application.handler;

import com.pragma.ms_small_square.application.dto.RestaurantRequest;
import com.pragma.ms_small_square.application.dto.RestaurantResponse;
import com.pragma.ms_small_square.application.mapper.RestaurantRequestMapper;
import com.pragma.ms_small_square.domain.api.IRestaurantServicePort;
import com.pragma.ms_small_square.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
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
}
