package com.pragma.ms_small_square.infrastructure.out.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

// Este DTO solo vive en la capa de infraestructura.
// Sirve como un contenedor de datos para la consulta optimizada.
@Getter
@AllArgsConstructor // ¡El constructor con todos los argumentos es crucial para que funcione la proyección!
public class RestaurantSummaryDto {
    private Long id;
    private String name;
    private String urlLogo; // Asegúrate de que el nombre coincida con el de tu Entity
}
