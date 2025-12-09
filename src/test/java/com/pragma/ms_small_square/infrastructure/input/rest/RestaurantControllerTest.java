package com.pragma.ms_small_square.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.ms_small_square.application.dto.request.RestaurantRequest;
import com.pragma.ms_small_square.application.handler.IRestaurantHandler;
import com.pragma.ms_small_square.domain.exception.UserNotOwnerException;
import com.pragma.ms_small_square.infrastructure.configuration.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
@Import(TestSecurityConfig.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IRestaurantHandler restaurantHandler;

    @Test
    @WithAnonymousUser
        // Simula una petición de un usuario no autenticado
    void createRestaurant_whenValidRequest_shouldReturnCreated() throws Exception {
        // Arrange: Crear una petición válida
        RestaurantRequest validRequest = new RestaurantRequest();
        validRequest.setName("Restaurante de Prueba");
        validRequest.setNit("123456789");
        validRequest.setAddress("Calle Falsa 123");
        validRequest.setPhone("+573001234567");
        validRequest.setUrlLogo("http://logo.com/logo.png");
        validRequest.setOwnerId(1L);

        // Configurar el mock para que no haga nada cuando se llame al handler
        when(restaurantHandler.saveRestaurant(any(RestaurantRequest.class))).thenReturn(null);

        // Act & Assert: Realizar la petición y esperar el estado 201 (Created)
        mockMvc.perform(post("/create-restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest))
                        .with(csrf())) // Incluir token CSRF para pasar la seguridad
                .andExpect(status().isCreated());

        // Verificar que el handler fue llamado exactamente una vez
        verify(restaurantHandler, times(1)).saveRestaurant(any(RestaurantRequest.class));
    }

    @Test
    @WithAnonymousUser
    void createRestaurant_whenInvalidRequest_shouldReturnBadRequest() throws Exception {
        // Arrange: Crear una petición inválida (nombre en blanco)
        RestaurantRequest invalidRequest = new RestaurantRequest();
        invalidRequest.setName(""); // Campo inválido
        invalidRequest.setNit("123456789");
        invalidRequest.setAddress("Calle Falsa 123");
        invalidRequest.setPhone("+573001234567");
        invalidRequest.setUrlLogo("http://logo.com/logo.png");
        invalidRequest.setOwnerId(1L);

        // Act & Assert: Realizar la petición y esperar el estado 400 (Bad Request)
        mockMvc.perform(post("/create-restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest))
                        .with(csrf()))
                .andExpect(status().isBadRequest());

        // Verificar que el handler NUNCA fue llamado, ya que la validación falló antes
        verify(restaurantHandler, never()).saveRestaurant(any(RestaurantRequest.class));
    }

    @Test
    @WithAnonymousUser
    void createRestaurant_whenHandlerThrowsBusinessException_shouldReturnNotFound() throws Exception {
        // Arrange: Crear una petición válida
        RestaurantRequest validRequest = new RestaurantRequest();
        validRequest.setName("Restaurante de Prueba");
        validRequest.setNit("123456789");
        validRequest.setAddress("Calle Falsa 123");
        validRequest.setPhone("+573001234567");
        validRequest.setUrlLogo("http://logo.com/logo.png");
        validRequest.setOwnerId(2L); // Un ID que causará un error de negocio

        // Configurar el mock para que lance una excepción de negocio
        // Esto simula el caso en que el UseCase determina que el ownerId no es válido
        doThrow(new UserNotOwnerException())
                .when(restaurantHandler).saveRestaurant(any(RestaurantRequest.class));

        // Act & Assert: Realizar la petición y esperar el estado 403 (Badrequest
        // Este estado se asume que es manejado por un ControllerAdvice global
        mockMvc.perform(post("/create-restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest))
                        .with(csrf()))
                .andExpect(status().isBadRequest());

        // Verificar que el handler fue llamado, ya que la excepción ocurrió dentro de él
        verify(restaurantHandler, times(1)).saveRestaurant(any(RestaurantRequest.class));
    }
}