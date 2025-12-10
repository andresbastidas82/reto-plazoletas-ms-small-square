package com.pragma.ms_small_square.domain.usecase;

import com.pragma.ms_small_square.domain.api.IRestaurantServicePort;
import com.pragma.ms_small_square.domain.exception.RestaurantNotFoundException;
import com.pragma.ms_small_square.domain.model.Restaurant;
import com.pragma.ms_small_square.domain.spi.IAuthenticationServicePort;
import com.pragma.ms_small_square.domain.spi.IRestaurantPersistencePort;
import org.springframework.data.domain.Page;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IAuthenticationServicePort authenticationServicePort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort,
                             IAuthenticationServicePort authenticationServicePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.authenticationServicePort = authenticationServicePort;
    }

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        restaurant.setOwnerId(authenticationServicePort.getUserIdOfToken());
        return restaurantPersistencePort.saveRestaurant(restaurant);
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        Restaurant restaurant = restaurantPersistencePort.getRestaurantById(id);
        if (restaurant == null) {
            throw new RestaurantNotFoundException();
        }
        return restaurant;
    }

    @Override
    public Page<Restaurant> getRestaurants(Integer page, Integer size) {
        return restaurantPersistencePort.getRestaurants(page, size);
    }
}
