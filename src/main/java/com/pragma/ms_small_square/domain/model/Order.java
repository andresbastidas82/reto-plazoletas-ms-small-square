package com.pragma.ms_small_square.domain.model;

import com.pragma.ms_small_square.domain.model.enums.OrderStateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Order {
    private Long id;
    private OrderStateEnum state;
    private LocalDateTime creationDate;
    private Restaurant restaurant;
    private User user; //cliente del pedido
    private String deliveryCode;
    private List<OrderDetails> dishes;
}
