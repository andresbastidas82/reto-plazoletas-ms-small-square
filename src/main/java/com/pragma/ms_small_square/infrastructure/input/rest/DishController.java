package com.pragma.ms_small_square.infrastructure.input.rest;

import com.pragma.ms_small_square.application.dto.DishRequest;
import com.pragma.ms_small_square.application.dto.DishResponse;
import com.pragma.ms_small_square.application.dto.DishUpdateRequest;
import com.pragma.ms_small_square.application.handler.IDishHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DishController {

    private final IDishHandler dishHandler;

    @PostMapping("/create-dish")
    public ResponseEntity<DishResponse> createDish(@Valid @RequestBody DishRequest dishRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dishHandler.saveDish(dishRequest));
    }

    @PutMapping("/update-dish/{id}")
    public ResponseEntity<DishResponse> updateDish(@PathVariable Long id, @Valid @RequestBody DishUpdateRequest dishRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dishHandler.updateDish(id, dishRequest));
    }

}
