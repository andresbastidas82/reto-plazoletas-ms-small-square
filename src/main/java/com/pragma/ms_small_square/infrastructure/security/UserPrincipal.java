package com.pragma.ms_small_square.infrastructure.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Objeto que representa al usuario autenticado en el contexto de seguridad.
 * Contiene la información extraída del token JWT.
 */
@Getter
@AllArgsConstructor
public class UserPrincipal {
    private final Long id;
    private final String name;
    private final String email;
    private final Long restaurantId;
}
