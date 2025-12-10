package com.pragma.ms_small_square.domain.model.enums;

import lombok.Getter;

@Getter
public enum OrderStateEnum {

    PENDING("Pendiente"),
    IN_PREPARATION("En preparación"),
    READY("Listo"),
    DELIVERED("Entregado");

    private final String description;

    OrderStateEnum(String description){
        this.description = description;
    }
}
