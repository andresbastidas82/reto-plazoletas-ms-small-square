package com.pragma.ms_small_square.application.handler;

import com.pragma.ms_small_square.application.dto.request.OrderRequest;
import com.pragma.ms_small_square.application.dto.response.OrderResponse;

public interface IOrderHandler {
    OrderResponse saveOrder(OrderRequest orderRequest);
}
