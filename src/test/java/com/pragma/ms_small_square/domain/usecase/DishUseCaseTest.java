package com.pragma.ms_small_square.domain.usecase;

import com.pragma.ms_small_square.domain.model.enums.DishCategoryEnum;
import com.pragma.ms_small_square.domain.spi.IUserClientPort;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.pragma.ms_small_square.domain.model.Dish;
import com.pragma.ms_small_square.domain.model.Restaurant;
import com.pragma.ms_small_square.domain.spi.IDishPersistencePort;
import com.pragma.ms_small_square.domain.exception.UserNotOwnerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {

    @Mock
    private IDishPersistencePort dishPersistencePort;

    @Mock
    private IUserClientPort userClientPort;

    @InjectMocks
    private DishUseCase dishUseCase;

    private Dish dishToSave;
    private Restaurant ownerRestaurant;
    private Long ownerId;

    @BeforeEach
    void setUp() {
        // Arrange común para los tests
        ownerId = 10L;

        ownerRestaurant = new Restaurant();
        ownerRestaurant.setId(1L);
        ownerRestaurant.setOwnerId(ownerId); // El ID del usuario propietario

        dishToSave = new Dish();
        dishToSave.setName("Pizza Hawaiana");
        dishToSave.setCategory(DishCategoryEnum.APPETIZER);
        dishToSave.setRestaurant(ownerRestaurant);
    }

    @Test
    void saveDish_whenUserIsOwnerAndRestaurantExists_shouldSaveDish() {
        // Arrange: Configurar mocks para el caso exitoso
        when(userClientPort.isOwner(any())).thenReturn(true);
        when(dishPersistencePort.saveDish(dishToSave)).thenReturn(dishToSave);

        // Act: Llamar al metodo que se está probando
        Dish savedDish = dishUseCase.saveDish(dishToSave);

        // Assert: Verificar los resultados y las interacciones
        assertNotNull(savedDish);
        assertEquals("Pizza Hawaiana", savedDish.getName());
        // Verificar que se guardó el plato una vez
        verify(dishPersistencePort, times(1)).saveDish(dishToSave);
    }

    @Test
    void saveDish_whenUserIsNotOwnerOfRestaurant_shouldThrowUserNotOwnerException() {
        // Act & Assert: Verificar que se lanza la excepción correcta
        assertThrows(UserNotOwnerException.class, () -> {
            dishUseCase.saveDish(dishToSave);
        });

        // Verificar que NUNCA se intentó guardar el plato
        verify(dishPersistencePort, never()).saveDish(any(Dish.class));
    }

}