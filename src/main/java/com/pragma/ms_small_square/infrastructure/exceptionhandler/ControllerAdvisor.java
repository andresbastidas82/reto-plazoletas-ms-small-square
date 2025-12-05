package com.pragma.ms_small_square.infrastructure.exceptionhandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.pragma.ms_small_square.domain.exception.RestaurantNotFoundException;
import com.pragma.ms_small_square.domain.exception.UserNotOwnerException;
import com.pragma.ms_small_square.domain.model.enums.DishCategoryEnum;
import com.pragma.ms_small_square.infrastructure.exception.DishCategoryNotFounException;
import com.pragma.ms_small_square.infrastructure.exception.ErrorRequestException;
import com.pragma.ms_small_square.infrastructure.exception.RestClientException;
import com.pragma.ms_small_square.infrastructure.exception.UserIsNotOwnerOfRestaurantException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor {

    private static final String ERRORS = "errors";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        List<String> errorMessages = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
        errors.put(ERRORS, errorMessages);
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, List<String>>> handleInvalidFormat(HttpMessageNotReadableException ex) {
        String message = "Invalid format or value in the JSON";
        if (ex.getCause() instanceof InvalidFormatException) {
            message = "The birthDate field must be in the format yyyy-MM-dd and be a valid date.";
        }
        Map<String, List<String>> error = new HashMap<>();
        error.put(ERRORS, List.of(message));
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(UserNotOwnerException.class)
    public ResponseEntity<Map<String, List<String>>> handleUserNotOwnerException(
            UserNotOwnerException userNotOwnerException) {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put(ERRORS, List.of(ExceptionResponse.INVALID_OWNER_ROLE.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(UserIsNotOwnerOfRestaurantException.class)
    public ResponseEntity<Map<String, List<String>>> handleUserNotOwnerException(
            UserIsNotOwnerOfRestaurantException userIsNotOwnerOfRestaurantException) {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put(ERRORS, List.of(ExceptionResponse.USER_IS_NOT_OWNER_OF_RESTAURANT.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<Map<String, List<String>>> handleAdultException(
            RestClientException restClientException) {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put(ERRORS, List.of(ExceptionResponse.REST_CLIENT.getMessage(), restClientException.getMessage()));
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errors);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleRestaurantNotFoundException(
            RestaurantNotFoundException restaurantNotFoundException) {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put(ERRORS, List.of(ExceptionResponse.RESTAURANT_NOT_FOUND.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(DishCategoryNotFounException.class)
    public ResponseEntity<Map<String, List<String>>> handleDishCategoryNotFoundException(
            DishCategoryNotFounException dishCategoryNotFounException) {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put(ERRORS, List.of(ExceptionResponse.DISH_CATEGORY_NOT_FOUND.getMessage(),
                    "Allowed categories: " + DishCategoryEnum.getCategoriesAsString()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, List<String>>> handleBadCredentialsException(BadCredentialsException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        // Devolvemos un mensaje genérico por seguridad.
        errors.put(ERRORS, List.of("Invalid credentials. Please check your email and password."));

        // Un fallo de credenciales debe devolver 401 UNAUTHORIZED
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, List<String>>> handleGlobalException(Exception ex, WebRequest request) {
        log.error("An unexpected error occurred: {} Hash code: {}", ex.getMessage(), ex.hashCode());
        Map<String, List<String>> errors = new HashMap<>();
        errors.put(ERRORS, List.of("An unexpected error occurred: " + ex.getMessage(), ((ServletWebRequest) request).getRequest().getRequestURI()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }

    @ExceptionHandler(ErrorRequestException.class)
    public ResponseEntity<Map<String, List<String>>> handleErrorRequestException(
            ErrorRequestException errorRequestException) {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put(ERRORS, List.of(errorRequestException.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
