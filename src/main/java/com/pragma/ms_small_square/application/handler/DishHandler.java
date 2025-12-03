package com.pragma.ms_small_square.application.handler;

import com.pragma.ms_small_square.application.dto.DishRequest;
import com.pragma.ms_small_square.application.dto.DishResponse;
import com.pragma.ms_small_square.application.mapper.DishRequestMapper;
import com.pragma.ms_small_square.domain.api.IDishServicePort;
import com.pragma.ms_small_square.domain.api.IRestaurantServicePort;
import com.pragma.ms_small_square.domain.model.Dish;
import com.pragma.ms_small_square.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishHandler implements IDishHandler {

    private final DishRequestMapper dishRequestMapper;
    private final IDishServicePort dishServicePort;
    private final IRestaurantServicePort restaurantServicePort;

    @Override
    public DishResponse saveDish(DishRequest dishRequest) {
        Restaurant restaurant = restaurantServicePort.getRestaurantById(dishRequest.getRestaurantId());
        Dish dish = dishRequestMapper.toDish(dishRequest);
        dish.setRestaurant(restaurant);
        dish.setState(Boolean.TRUE);
        Dish dishSaved = dishServicePort.saveDish(dish);
        return dishRequestMapper.toDishResponse(dishSaved);
    }
}
