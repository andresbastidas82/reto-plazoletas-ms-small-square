package com.pragma.ms_small_square.domain.api;

import com.pragma.ms_small_square.domain.model.Order;
import com.pragma.ms_small_square.domain.model.OrderDetails;
import com.pragma.ms_small_square.domain.model.Restaurant;

import java.util.List;

public interface IOrderServicePort {
    Order saveOrder(Restaurant restaurant, List<OrderDetails> orderDetails);
}
