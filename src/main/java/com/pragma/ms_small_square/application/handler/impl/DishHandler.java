package com.pragma.ms_small_square.application.handler.impl;

import com.pragma.ms_small_square.application.dto.request.DishRequest;
import com.pragma.ms_small_square.application.dto.response.DishResponse;
import com.pragma.ms_small_square.application.dto.request.DishUpdateRequest;
import com.pragma.ms_small_square.application.handler.IDishHandler;
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
        Dish dishSaved = dishServicePort.saveDish(dish, restaurant);
        return dishRequestMapper.toDishResponse(dishSaved);
    }

    @Override
    public DishResponse updateDish(Long id, DishUpdateRequest dishUpdateRequest) {
        Dish dishUpdate = dishRequestMapper.toDishUpdate(dishUpdateRequest);
        Dish currentDish = dishServicePort.getDishById(id);
        Dish dishSaved = dishServicePort.updateDish(currentDish, dishUpdate);
        return dishRequestMapper.toDishResponse(dishSaved);
    }

    @Override
    public DishResponse updateStateDish(Long id, Boolean state) {
        Dish dish = dishServicePort.getDishById(id);
        Dish dishSaved = dishServicePort.updateStateDish(dish, state);
        return dishRequestMapper.toDishResponse(dishSaved);
    }

}
