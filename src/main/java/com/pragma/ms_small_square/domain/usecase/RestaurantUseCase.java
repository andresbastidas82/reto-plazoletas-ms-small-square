package com.pragma.ms_small_square.domain.usecase;

import com.pragma.ms_small_square.domain.api.IRestaurantServicePort;
import com.pragma.ms_small_square.domain.model.Restaurant;
import com.pragma.ms_small_square.domain.spi.IRestaurantPersistencePort;
import com.pragma.ms_small_square.domain.spi.IUserClientPort;
import com.pragma.ms_small_square.infrastructure.exception.InvalidOwnerRoleException;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserClientPort userClientPort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserClientPort userClientPort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userClientPort = userClientPort;
    }

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        if (!userClientPort.isOwner(restaurant.getOwnerId())) {
            throw new InvalidOwnerRoleException();
        }
        return restaurantPersistencePort.saveRestaurant(restaurant);
    }
}
