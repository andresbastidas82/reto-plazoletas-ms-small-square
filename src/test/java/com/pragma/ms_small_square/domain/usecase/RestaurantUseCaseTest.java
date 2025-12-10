package com.pragma.ms_small_square.domain.usecase;

import com.pragma.ms_small_square.domain.model.Restaurant;
import com.pragma.ms_small_square.domain.spi.IAuthenticationServicePort;
import com.pragma.ms_small_square.domain.spi.IRestaurantPersistencePort;
import com.pragma.ms_small_square.infrastructure.exception.InvalidOwnerRoleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IAuthenticationServicePort authenticationServicePort;

    /*@Mock
    private IUserClientPort userClientPort;*/

    private RestaurantUseCase restaurantUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurantUseCase = new RestaurantUseCase(restaurantPersistencePort, authenticationServicePort);
    }

    @Test
    void saveRestaurant_whenUserIsOwner_shouldSaveRestaurant() {
        // Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(1L);
        //when(userClientPort.isOwner(1L)).thenReturn(true);
        when(restaurantPersistencePort.saveRestaurant(restaurant)).thenReturn(restaurant);

        // Act
        Restaurant savedRestaurant = restaurantUseCase.saveRestaurant(restaurant);

        // Assert
        assertNotNull(savedRestaurant);
        //verify(userClientPort).isOwner(1L);
        verify(restaurantPersistencePort).saveRestaurant(restaurant);
    }

    @Test
    void saveRestaurant_whenUserIsNotOwner_shouldThrowException() {
        // Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(1L);
        //when(userClientPort.isOwner(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(InvalidOwnerRoleException.class, () -> restaurantUseCase.saveRestaurant(restaurant));
        //verify(userClientPort).isOwner(1L);
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }
}