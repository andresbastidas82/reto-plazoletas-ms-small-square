package com.pragma.ms_small_square.domain.usecase;

import com.pragma.ms_small_square.domain.api.IOrderServicePort;
import com.pragma.ms_small_square.domain.exception.UserPendingOrdersException;
import com.pragma.ms_small_square.domain.model.Order;
import com.pragma.ms_small_square.domain.model.OrderDetails;
import com.pragma.ms_small_square.domain.model.Restaurant;
import com.pragma.ms_small_square.domain.model.User;
import com.pragma.ms_small_square.domain.model.enums.OrderStateEnum;
import com.pragma.ms_small_square.domain.spi.IAuthenticationServicePort;
import com.pragma.ms_small_square.domain.spi.IOrderPersistencePort;
import com.pragma.ms_small_square.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IAuthenticationServicePort authenticationServicePort;
    private final IUserPersistencePort userPersistencePort;

    @Override
    public Order saveOrder(Restaurant restaurant, List<OrderDetails> dishes) {
        validOrdersOfUser();
        Order order = new Order();
        order.setRestaurant(restaurant);
        order.setDishes(dishes);
        order.setState(OrderStateEnum.PENDING);
        order.setUser(registerCustomer());
        return orderPersistencePort.saveOrder(order);
    }

    private User registerCustomer() {
        String nameUserToken = authenticationServicePort.getNameOfToken();
        Long idUserToken = authenticationServicePort.getUserIdOfToken();
        return userPersistencePort.validateUser(new User(idUserToken, nameUserToken));
    }

    private void validOrdersOfUser( ){
        Long idUserToken = authenticationServicePort.getUserIdOfToken();
        List<OrderStateEnum> states = List.of(OrderStateEnum.PENDING, OrderStateEnum.READY, OrderStateEnum.IN_PREPARATION);
        List<Order> orders = orderPersistencePort.getOrderByUserAndStates(idUserToken, states, 0, 10);
        if (orders != null && !orders.isEmpty()) {
            throw new UserPendingOrdersException("The user has pending orders");
        }
    }
}
