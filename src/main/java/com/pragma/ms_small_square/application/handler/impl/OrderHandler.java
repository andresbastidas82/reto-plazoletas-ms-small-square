package com.pragma.ms_small_square.application.handler.impl;

import com.pragma.ms_small_square.application.dto.request.OrderRequest;
import com.pragma.ms_small_square.application.dto.response.OrderResponse;
import com.pragma.ms_small_square.application.handler.IOrderHandler;
import com.pragma.ms_small_square.application.mapper.OrderRequestMapper;
import com.pragma.ms_small_square.domain.api.IDishServicePort;
import com.pragma.ms_small_square.domain.api.IOrderServicePort;
import com.pragma.ms_small_square.domain.api.IRestaurantServicePort;
import com.pragma.ms_small_square.domain.model.Dish;
import com.pragma.ms_small_square.domain.model.Order;
import com.pragma.ms_small_square.domain.model.OrderDetails;
import com.pragma.ms_small_square.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderHandler implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final IDishServicePort dishServicePort;
    private final IRestaurantServicePort restaurantServicePort;
    private final OrderRequestMapper orderRequestMapper;

    @Override
    public OrderResponse saveOrder(OrderRequest orderRequest) {
        List<OrderDetails> orderDetails = new ArrayList<>();
        orderRequest.getDishes().forEach(details -> {
            Dish dish = dishServicePort.getDishById(details.getDishId());
            OrderDetails orderDetail = OrderDetails.builder().dish(dish).quantity(details.getQuantity()).price(dish.getPrice()).build();
            orderDetails.add(orderDetail);
        });
        Restaurant restaurant = restaurantServicePort.getRestaurantById(orderRequest.getRestaurantId());
        Order order = orderServicePort.saveOrder(restaurant, orderDetails);
        return orderRequestMapper.toOrderResponse(order);
    }

    @Override
    public Page<OrderResponse> getOrdersByState(String state, int page, int size) {
        return orderServicePort.getOrdersByState(state, page, size).map(orderRequestMapper::toOrderResponse);
    }
}
