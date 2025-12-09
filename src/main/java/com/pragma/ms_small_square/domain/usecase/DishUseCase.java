package com.pragma.ms_small_square.domain.usecase;

import com.pragma.ms_small_square.domain.api.IDishServicePort;
import com.pragma.ms_small_square.domain.exception.DishNotFoundException;
import com.pragma.ms_small_square.domain.model.Dish;
import com.pragma.ms_small_square.domain.model.Restaurant;
import com.pragma.ms_small_square.domain.spi.IAuthenticationServicePort;
import com.pragma.ms_small_square.domain.spi.IDishPersistencePort;
import com.pragma.ms_small_square.domain.exception.ErrorRequestException;
import com.pragma.ms_small_square.domain.exception.UserIsNotOwnerOfRestaurantException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final IAuthenticationServicePort authenticationServicePort;

    @Override
    public Dish saveDish(Dish dish, Restaurant restaurant) {
        Long userIdToken = authenticationServicePort.getUserIdOfToken();
        validateUserIsOwnerOfRestaurant(restaurant.getOwnerId(), userIdToken);
        dish.setOwnerId(userIdToken);
        dish.setRestaurant(restaurant);
        dish.setState(Boolean.TRUE);
        return dishPersistencePort.saveDish(dish);
    }

    @Override
    public Dish updateDish(Dish currentDish, Dish dishUpdate) {
        Long userIdToken = authenticationServicePort.getUserIdOfToken();
        validateUserIsOwnerOfRestaurant(currentDish.getRestaurant().getOwnerId(), userIdToken);
        currentDish.setPrice(dishUpdate.getPrice());
        currentDish.setDescription(dishUpdate.getDescription());
        return dishPersistencePort.saveDish(currentDish);
    }

    @Override
    public Dish updateStateDish(Dish dish, Boolean state) {
        if (dish == null) {
            throw new ErrorRequestException("The id of dish is required.");
        }
        if (state == null) {
            throw new ErrorRequestException("The state of dish is required.");
        }
        Long userIdToken = authenticationServicePort.getUserIdOfToken();
        validateUserIsOwnerOfRestaurant(dish.getRestaurant().getOwnerId(), userIdToken);
        dish.setState(state);
        Dish dishSaved = dishPersistencePort.saveDish(dish);
        return dishPersistencePort.saveDish(dishSaved);
    }

    @Override
    public Dish getDishById(Long id) {
        Dish dish = dishPersistencePort.getDishById(id);
        if (dish == null) {
            throw new DishNotFoundException();
        }
        return dish;
    }

    private void validateUserIsOwnerOfRestaurant(Long userIdOfRestaurant, Long userIdToken) {
        if(!Objects.equals(userIdOfRestaurant, userIdToken)){
            throw new UserIsNotOwnerOfRestaurantException();
        }
    }
}
