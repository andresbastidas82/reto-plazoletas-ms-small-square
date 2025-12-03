package com.pragma.ms_small_square.infrastructure.exceptionhandler;

import lombok.Getter;

@Getter
public enum ExceptionResponse {
    INVALID_OWNER_ROLE("The user does not have the owner role."),
    REST_CLIENT("Error querying data from the user microservice.");

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }
}
