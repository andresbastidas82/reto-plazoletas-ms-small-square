package com.pragma.ms_small_square.domain.usecase;

import com.pragma.ms_small_square.domain.api.IOrderServicePort;
import com.pragma.ms_small_square.domain.exception.ErrorRequestException;
import com.pragma.ms_small_square.domain.exception.OrderNotFoundException;
import com.pragma.ms_small_square.domain.exception.UserPendingOrdersException;
import com.pragma.ms_small_square.domain.model.Order;
import com.pragma.ms_small_square.domain.model.OrderDetails;
import com.pragma.ms_small_square.domain.model.Restaurant;
import com.pragma.ms_small_square.domain.model.User;
import com.pragma.ms_small_square.domain.model.enums.OrderStateEnum;
import com.pragma.ms_small_square.domain.spi.IAuthenticationServicePort;
import com.pragma.ms_small_square.domain.spi.INotificationClientPort;
import com.pragma.ms_small_square.domain.spi.IOrderPersistencePort;
import com.pragma.ms_small_square.domain.spi.IUserClientPort;
import com.pragma.ms_small_square.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IAuthenticationServicePort authenticationServicePort;
    private final IUserPersistencePort userPersistencePort;
    private final INotificationClientPort notificationClientPort;
    private final IUserClientPort userClientPort;

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

    @Override
    public Page<Order> getOrdersByState(String state, int page, int size) {
        OrderStateEnum orderStateEnum = OrderStateEnum.getOrderState(state);
        Long idRestaurantToken = authenticationServicePort.getRestaurantIdOfToken();
        return orderPersistencePort.getOrdersByState(orderStateEnum, page, size, idRestaurantToken);
    }

    @Override
    public Order assignOrderToEmployee(Order order) {
        if(!order.getState().equals(OrderStateEnum.PENDING)) {
            throw new ErrorRequestException("The order is not in pending state");
        }
        order.setEmployee(registerCustomer());
        order.setState(OrderStateEnum.IN_PREPARATION);
        return orderPersistencePort.saveOrder(order);
    }

    @Override
    public Order getOrderById(Long orderId) {
        Order order = orderPersistencePort.getOrderById(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order not found");
        }
        return order;
    }

    @Override
    public Order notifyOrderReady(Order order) {
        if(!order.getState().equals(OrderStateEnum.IN_PREPARATION)) {
            throw new ErrorRequestException("The order is not in preparation state");
        }
        String code = generateSixDigitCode();
        Boolean resultNotification = sendNotificationOrder(code, order);
        if (Boolean.FALSE.equals(resultNotification)) {
            throw new ErrorRequestException("Error sending notification");
        }
        order.setDeliveryCode(code);
        order.setState(OrderStateEnum.READY);
        return orderPersistencePort.saveOrder(order);
    }

    @Override
    public Order deliverOrder(Order order, String verificationCode) {
        if(!order.getState().equals(OrderStateEnum.READY)) {
            throw new ErrorRequestException("The order is not in ready state");
        }
        if (!verificationCode.equals(order.getDeliveryCode())) {
            throw new ErrorRequestException("Invalid verification code");
        }
        order.setState(OrderStateEnum.DELIVERED);
        return orderPersistencePort.saveOrder(order);
    }

    @Override
    public Order cancelOrder(Order order) {
        if(order.getState().equals(OrderStateEnum.CANCELED)) {
            throw new ErrorRequestException("The order is already canceled");
        }
        if(!order.getState().equals(OrderStateEnum.PENDING)) {
            throw new ErrorRequestException("Lo sentimos, tu pedido ya está en preparación y no puede cancelarse");
        }
        order.setState(OrderStateEnum.CANCELED);
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

    private Boolean sendNotificationOrder(String code, Order order) {
        String phone = userClientPort.getPhoneCustomer(order.getUser().getId());
        if (phone == null) {
            throw new ErrorRequestException("Phone number not found");
        }
        String message = "Su pedido esta listo! Su pin de seguridad para reclamar el pedido es: " + code;
        return notificationClientPort.sendNotification(phone, message);
    }

    private String generateSixDigitCode() {
        Random random = new Random();
        int number = random.nextInt(1_000_000); // genera entre 0 y 999999
        return String.format("%06d", number);   // fuerza 6 dígitos con ceros
    }
}
