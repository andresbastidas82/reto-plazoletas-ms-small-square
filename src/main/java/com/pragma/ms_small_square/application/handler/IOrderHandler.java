package com.pragma.ms_small_square.application.handler;

import com.pragma.ms_small_square.application.dto.request.OrderRequest;
import com.pragma.ms_small_square.application.dto.response.OrderResponse;
import org.springframework.data.domain.Page;

public interface IOrderHandler {
    OrderResponse saveOrder(OrderRequest orderRequest);

    Page<OrderResponse> getOrdersByState(String state, int page, int size);

    OrderResponse assignOrderToEmployee(Long orderId);

    OrderResponse notifyOrderReady(Long orderId);

    OrderResponse deliverOrder(Long orderId, String verificationCode);
}
