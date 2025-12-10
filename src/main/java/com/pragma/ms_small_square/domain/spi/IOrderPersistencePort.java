package com.pragma.ms_small_square.domain.spi;

import com.pragma.ms_small_square.domain.model.Order;
import com.pragma.ms_small_square.domain.model.enums.OrderStateEnum;

import java.util.List;

public interface IOrderPersistencePort {
    Order saveOrder(Order order);

    List<Order> getOrderByUserAndStates(Long userId, List<OrderStateEnum> states, int page, int size);
}
