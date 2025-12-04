package com.pragma.ms_small_square.application.handler;

import com.pragma.ms_small_square.application.dto.DishRequest;
import com.pragma.ms_small_square.application.dto.DishResponse;
import com.pragma.ms_small_square.application.dto.DishUpdateRequest;
import com.pragma.ms_small_square.application.mapper.DishRequestMapper;
import com.pragma.ms_small_square.domain.api.IDishServicePort;
import com.pragma.ms_small_square.domain.api.IRestaurantServicePort;
import com.pragma.ms_small_square.domain.model.Dish;
import com.pragma.ms_small_square.domain.model.Restaurant;
import com.pragma.ms_small_square.infrastructure.exception.UserIsNotOwnerOfRestaurantException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DishHandler implements IDishHandler {

    private final DishRequestMapper dishRequestMapper;
    private final IDishServicePort dishServicePort;
    private final IRestaurantServicePort restaurantServicePort;

    @Override
    public DishResponse saveDish(DishRequest dishRequest) {
        Restaurant restaurant = restaurantServicePort.getRestaurantById(dishRequest.getRestaurantId());
        if(!Objects.equals(restaurant.getOwnerId(), dishRequest.getOwnerId())) {
            throw new UserIsNotOwnerOfRestaurantException();
        }
        Dish dish = dishRequestMapper.toDish(dishRequest);
        dish.setRestaurant(restaurant);
        dish.setState(Boolean.TRUE);
        Dish dishSaved = dishServicePort.saveDish(dish);
        return dishRequestMapper.toDishResponse(dishSaved);
    }

    @Override
    public DishResponse updateDish(Long id, DishUpdateRequest dishUpdateRequest) {
        Dish dish = dishServicePort.getDishById(id);
        dish.setPrice(dishUpdateRequest.getPrice());
        dish.setDescription(dishUpdateRequest.getDescription());
        Dish dishSaved = dishServicePort.saveDish(dish);
        return dishRequestMapper.toDishResponse(dishSaved);
    }
}
