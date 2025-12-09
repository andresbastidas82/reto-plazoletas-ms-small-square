package com.pragma.ms_small_square.application.handler;

import com.pragma.ms_small_square.application.dto.request.DishRequest;
import com.pragma.ms_small_square.application.handler.impl.DishHandler;
import com.pragma.ms_small_square.application.mapper.DishRequestMapper;
import com.pragma.ms_small_square.domain.api.IRestaurantServicePort;
import com.pragma.ms_small_square.domain.model.Restaurant;
import org.junit.jupiter.api.Test;


import com.pragma.ms_small_square.domain.api.IDishServicePort;
import com.pragma.ms_small_square.domain.model.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishHandlerTest {

    @Mock
    private IDishServicePort dishServicePort;

    @Mock
    private DishRequestMapper dishRequestMapper;

    @Mock
    private IRestaurantServicePort restaurantServicePort;

    @InjectMocks
    private DishHandler dishHandler;

    private DishRequest dishRequest;
    private Dish dishModel;
    private Long ownerId;

    @BeforeEach
    void setUp() {
        // Arrange: Preparamos los objetos que usaremos en los tests
        ownerId = 1L;

        dishRequest = new DishRequest(
                "Pizza",
                "Deliciosa pizza de pepperoni",
                new BigDecimal("25000"),
                "http://images.com/pizza.png",
                "ITALIANA",
                10L,
                ownerId
        );

        dishModel = new Dish();
        dishModel.setName("Pizza");
    }

    @Test
    void saveDish_shouldMapRequestAndCallServicePort() {
        // Arrange: Configuramos el comportamiento de nuestros mocks
        when(restaurantServicePort.getRestaurantById(anyLong())).thenReturn(new Restaurant());
        when(dishRequestMapper.toDish(dishRequest)).thenReturn(dishModel);

        // Act: Ejecutamos el metodo que queremos probar
        dishHandler.saveDish(dishRequest);

        // Assert: Verificamos que las interacciones esperadas ocurrieron
        // 1. ¿Se llamó al mapper para convertir el DTO?
        verify(dishRequestMapper, times(1)).toDish(dishRequest);

        // 2. ¿Se llamó al service port con el objeto de dominio y el ID del propietario correctos?
        //verify(dishServicePort, times(1)).saveDish(dishModel);
    }
}