package com.pragma.ms_small_square.infrastructure.input.rest;

import com.pragma.ms_small_square.application.dto.request.OrderRequest;
import com.pragma.ms_small_square.application.dto.response.OrderResponse;
import com.pragma.ms_small_square.application.handler.IOrderHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/small-square")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderHandler orderHandler;

    @PostMapping("/create-order")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderHandler.saveOrder(orderRequest));
    }
}
