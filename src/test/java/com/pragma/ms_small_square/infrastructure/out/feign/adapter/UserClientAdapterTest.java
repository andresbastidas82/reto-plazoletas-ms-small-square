package com.pragma.ms_small_square.infrastructure.out.feign.adapter;

import com.pragma.ms_small_square.infrastructure.out.feign.IUserFeignClient;
import com.pragma.ms_small_square.infrastructure.out.feign.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserClientAdapterTest {

    @Mock
    private IUserFeignClient userFeignClient; // El mock que simula la llamada externa

    @InjectMocks
    private UserClientAdapter userClientAdapter; // La instancia de la clase que queremos probar

    private UserResponse ownerResponse;
    private UserResponse customerResponse;

    @BeforeEach
    void setUp() {
        // Arrange global: Preparamos objetos de respuesta comunes para los tests
        ownerResponse = new UserResponse();
        ownerResponse.setId(1L);
        ownerResponse.setRole("OWNER"); // Asumimos que el rol de propietario se llama "OWNER"

        customerResponse = new UserResponse();
        customerResponse.setId(2L);
        customerResponse.setRole("CUSTOMER"); // Asumimos otro rol para el caso negativo
    }

    @Test
    void getUserById_whenUserExists_shouldReturnUserResponse() {
        // Arrange: Configurar el mock para que devuelva un usuario cuando se le llame
        Long userId = 1L;
        when(userFeignClient.getUserById(userId)).thenReturn(ownerResponse);

        // Act: Llamar al método que estamos probando
        UserResponse actualResponse = userClientAdapter.getUserById(userId);

        // Assert: Verificar que el resultado es el esperado
        assertNotNull(actualResponse);
        assertEquals(userId, actualResponse.getId());
        assertEquals("OWNER", actualResponse.getRole());
        verify(userFeignClient, times(1)).getUserById(userId); // Verificar que el cliente Feign fue llamado una vez
    }

    @Test
    void getUserById_whenUserDoesNotExist_shouldReturnNull() {
        // Arrange: Configurar el mock para que devuelva null (o lance una excepción que el adapter maneje)
        Long userId = 99L;
        when(userFeignClient.getUserById(userId)).thenReturn(null);

        // Act: Llamar al método
        UserResponse actualResponse = userClientAdapter.getUserById(userId);

        // Assert: Verificar que el resultado es nulo
        assertNull(actualResponse);
        verify(userFeignClient, times(1)).getUserById(userId);
    }

    @Test
    void isOwner_whenUserIsOwner_shouldReturnTrue() {
        // Arrange: Configurar el mock para que devuelva un usuario con rol "OWNER"
        Long ownerId = 1L;
        when(userFeignClient.getUserById(ownerId)).thenReturn(ownerResponse);

        // Act: Llamar al método
        boolean isOwner = userClientAdapter.isOwner(ownerId);

        // Assert: Verificar que el resultado es verdadero
        assertTrue(isOwner);
        verify(userFeignClient, times(1)).getUserById(ownerId);
    }

    @Test
    void isOwner_whenUserIsNotOwner_shouldReturnFalse() {
        // Arrange: Configurar el mock para que devuelva un usuario con un rol diferente
        Long customerId = 2L;
        when(userFeignClient.getUserById(customerId)).thenReturn(customerResponse);

        // Act: Llamar al método
        boolean isOwner = userClientAdapter.isOwner(customerId);

        // Assert: Verificar que el resultado es falso
        assertFalse(isOwner);
        verify(userFeignClient, times(1)).getUserById(customerId);
    }

    @Test
    void isOwner_whenUserDoesNotExist_shouldReturnFalse() {
        // Arrange: Configurar el mock para que simule que el usuario no existe
        Long nonExistentUserId = 99L;
        when(userFeignClient.getUserById(nonExistentUserId)).thenReturn(null);

        // Act: Llamar al método
        boolean isOwner = userClientAdapter.isOwner(nonExistentUserId);

        // Assert: Verificar que el resultado es falso
        assertFalse(isOwner);
        verify(userFeignClient, times(1)).getUserById(nonExistentUserId);
    }
}