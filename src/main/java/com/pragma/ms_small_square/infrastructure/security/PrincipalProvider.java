package com.pragma.ms_small_square.infrastructure.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PrincipalProvider {
    /**
     * Obtiene el ID del usuario autenticado desde el contexto de seguridad.
     * @return El ID del usuario (Long).
     * @throws ClassCastException si el principal no es del tipo esperado (Long).
     */
    public Long getUserId() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
