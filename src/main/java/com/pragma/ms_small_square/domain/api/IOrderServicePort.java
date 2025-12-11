package com.pragma.ms_small_square.domain.api;

import com.pragma.ms_small_square.domain.model.Order;
import com.pragma.ms_small_square.domain.model.OrderDetails;
import com.pragma.ms_small_square.domain.model.Restaurant;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrderServicePort {
    Order saveOrder(Restaurant restaurant, List<OrderDetails> orderDetails);

    Page<Order> getOrdersByState(String state, int page, int size);

    Order assignOrderToEmployee(Order order);

    Order getOrderById(Long orderId);

    Order notifyOrderReady(Order order);
}
