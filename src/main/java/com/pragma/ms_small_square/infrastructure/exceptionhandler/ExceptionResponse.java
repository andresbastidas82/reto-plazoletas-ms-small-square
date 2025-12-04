package com.pragma.ms_small_square.infrastructure.exceptionhandler;

import lombok.Getter;

@Getter
public enum ExceptionResponse {
    INVALID_OWNER_ROLE("The user does not have the owner role."),
    RESTAURANT_NOT_FOUND("The restaurant was not found."),
    DISH_NOT_FOUND("The dish was not found."),
    DISH_CATEGORY_NOT_FOUND("The dish category was not found."),
    USER_IS_NOT_OWNER_OF_RESTAURANT("The user is not owner of the restaurante."),
    REST_CLIENT("Error querying data from the user microservice.");

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }
}
