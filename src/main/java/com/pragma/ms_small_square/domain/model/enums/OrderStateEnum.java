package com.pragma.ms_small_square.domain.model.enums;

import com.pragma.ms_small_square.domain.exception.OrderStateNotFoundException;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
public enum OrderStateEnum {

    PENDING("Pendiente"),
    IN_PREPARATION("En preparación"),
    READY("Listo"),
    CANCELED("Cancelado"),
    DELIVERED("Entregado");

    private final String description;

    OrderStateEnum(String description){
        this.description = description;
    }

    public static String getOrderStatesAsString() {
        return Arrays.stream(OrderStateEnum.values())
                .map(item -> item.name() + " (" + item.getDescription() + ")")
                .collect(Collectors.joining(", "));
    }

    public static OrderStateEnum getOrderState(String name) {
        try{
            return OrderStateEnum.valueOf(name);
        } catch (Exception e){
            throw new OrderStateNotFoundException("The order state does not exist");
        }
    }
}
