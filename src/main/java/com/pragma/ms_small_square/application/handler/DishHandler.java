package com.pragma.ms_small_square.application.handler;

import com.pragma.ms_small_square.application.dto.DishRequest;
import com.pragma.ms_small_square.application.dto.DishResponse;
import com.pragma.ms_small_square.application.dto.DishUpdateRequest;
import com.pragma.ms_small_square.application.mapper.DishRequestMapper;
import com.pragma.ms_small_square.domain.api.IDishServicePort;
import com.pragma.ms_small_square.domain.api.IRestaurantServicePort;
import com.pragma.ms_small_square.domain.model.Dish;
import com.pragma.ms_small_square.domain.model.Restaurant;
import com.pragma.ms_small_square.infrastructure.exception.ErrorRequestException;
import com.pragma.ms_small_square.infrastructure.exception.UserIsNotOwnerOfRestaurantException;
import com.pragma.ms_small_square.infrastructure.security.PrincipalProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DishHandler implements IDishHandler {

    private final DishRequestMapper dishRequestMapper;
    private final IDishServicePort dishServicePort;
    private final IRestaurantServicePort restaurantServicePort;
    private final PrincipalProvider principalProvider;

    @Override
    public DishResponse saveDish(DishRequest dishRequest) {
        Restaurant restaurant = restaurantServicePort.getRestaurantById(dishRequest.getRestaurantId());
        validateUserIsOwnerOfRestaurant(restaurant);
        Dish dish = dishRequestMapper.toDish(dishRequest);
        dish.setRestaurant(restaurant);
        dish.setState(Boolean.TRUE);
        dish.setOwnerId(principalProvider.getUserId());
        Dish dishSaved = dishServicePort.saveDish(dish);
        return dishRequestMapper.toDishResponse(dishSaved);
    }

    @Override
    public DishResponse updateDish(Long id, DishUpdateRequest dishUpdateRequest) {
        Dish dish = dishServicePort.getDishById(id);
        validateUserIsOwnerOfRestaurant(dish.getRestaurant());
        dish.setPrice(dishUpdateRequest.getPrice());
        dish.setDescription(dishUpdateRequest.getDescription());
        Dish dishSaved = dishServicePort.saveDish(dish);
        return dishRequestMapper.toDishResponse(dishSaved);
    }

    @Override
    public DishResponse updateStateDish(Long id, String state) {
        if (id == null) {
            throw new ErrorRequestException("The id of dish is required.");
        }
        if (state == null) {
            throw new ErrorRequestException("The state of dish is required.");
        }
        if (!state.equals("ACTIVE") && !state.equals("INACTIVE")) {
            throw new ErrorRequestException("The state of dish is invalid. Only ACTIVE or INACTIVE are allowed.");
        }
        Dish dish = dishServicePort.getDishById(id);
        validateUserIsOwnerOfRestaurant(dish.getRestaurant());
        dish.setState(state.equals("ACTIVE") ? Boolean.TRUE : Boolean.FALSE);
        Dish dishSaved = dishServicePort.saveDish(dish);
        return dishRequestMapper.toDishResponse(dishSaved);
    }

    private void validateUserIsOwnerOfRestaurant(Restaurant restaurant) {
        Long userIdToken = principalProvider.getUserId();
        if(!Objects.equals(restaurant.getOwnerId(), userIdToken)){
            throw new UserIsNotOwnerOfRestaurantException();
        }
    }


}
