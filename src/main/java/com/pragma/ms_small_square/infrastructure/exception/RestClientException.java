package com.pragma.ms_small_square.infrastructure.exception;

public class RestClientException extends RuntimeException {
    public RestClientException(String message) {
        super(message);
    }
}
