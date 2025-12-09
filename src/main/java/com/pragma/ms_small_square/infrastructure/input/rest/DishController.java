package com.pragma.ms_small_square.infrastructure.input.rest;

import com.pragma.ms_small_square.application.dto.request.DishRequest;
import com.pragma.ms_small_square.application.dto.response.DishResponse;
import com.pragma.ms_small_square.application.dto.request.DishUpdateRequest;
import com.pragma.ms_small_square.application.handler.IDishHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/small-square")
@RequiredArgsConstructor
public class DishController {

    private final IDishHandler dishHandler;

    @PostMapping("/create-dish")
    public ResponseEntity<DishResponse> createDish(@Valid @RequestBody DishRequest dishRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dishHandler.saveDish(dishRequest));
    }

    @PutMapping("/update-dish/{id}")
    public ResponseEntity<DishResponse> updateDish(@PathVariable Long id, @Valid @RequestBody DishUpdateRequest dishRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(dishHandler.updateDish(id, dishRequest));
    }

    @GetMapping("/update-status-dish/{id}")
    public ResponseEntity<DishResponse> updateStateDish(@PathVariable("id") Long id, @RequestParam Boolean state) {
        return ResponseEntity.status(HttpStatus.OK).body(dishHandler.updateStateDish(id, state));
    }

    @GetMapping("/dishes-by-restaurant")
    public ResponseEntity<Page<DishResponse>> getDishesByRestaurant(
            @RequestParam Long restaurantId,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(dishHandler.getDishesByRestaurant(restaurantId, category, page, size));
    }

}
