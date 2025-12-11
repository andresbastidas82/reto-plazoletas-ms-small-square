package com.pragma.ms_small_square.infrastructure.input.rest;

import com.pragma.ms_small_square.application.dto.request.OrderRequest;
import com.pragma.ms_small_square.application.dto.response.OrderResponse;
import com.pragma.ms_small_square.application.handler.IOrderHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
public class OrderController {

    private final IOrderHandler orderHandler;

    @PostMapping("/create-order")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderHandler.saveOrder(orderRequest));
    }

    @GetMapping("/orders-by-state")
    public ResponseEntity<Page<OrderResponse>> getOrdersByState(
            @RequestParam String state,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(orderHandler.getOrdersByState(state, page, size));
    }

    @PutMapping("/assign-order-to-employee/{orderId}")
    public ResponseEntity<OrderResponse> assignOrderToEmployee(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderHandler.assignOrderToEmployee(orderId));
    }

    @PutMapping("/notify-order-ready/{orderId}")
    public ResponseEntity<OrderResponse> notifyOrderReady(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderHandler.notifyOrderReady(orderId));
    }

    @PutMapping("/deliver-order/{orderId}")
    public ResponseEntity<OrderResponse> deliverOrder(
            @PathVariable Long orderId,
            @RequestParam String code) {
        return ResponseEntity.ok(orderHandler.deliverOrder(orderId, code));
    }

    @PutMapping("/cancel-order/{orderId}")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderHandler.cancelOrder(orderId));
    }
}
