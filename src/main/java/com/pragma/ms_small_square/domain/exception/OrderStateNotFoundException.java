package com.pragma.ms_small_square.domain.exception;

public class OrderStateNotFoundException extends RuntimeException {
    public OrderStateNotFoundException(String message) {
        super(message);
    }
}
