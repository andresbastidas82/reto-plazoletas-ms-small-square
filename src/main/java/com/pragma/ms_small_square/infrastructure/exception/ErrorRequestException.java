package com.pragma.ms_small_square.infrastructure.exception;

public class ErrorRequestException extends RuntimeException {
    public ErrorRequestException(String message) {
        super(message);
    }

}
