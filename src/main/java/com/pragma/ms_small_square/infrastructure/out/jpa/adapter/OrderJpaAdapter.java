package com.pragma.ms_small_square.infrastructure.out.jpa.adapter;

import com.pragma.ms_small_square.domain.model.Order;
import com.pragma.ms_small_square.domain.model.enums.OrderStateEnum;
import com.pragma.ms_small_square.domain.spi.IOrderPersistencePort;
import com.pragma.ms_small_square.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.ms_small_square.infrastructure.out.jpa.mapper.OrderEntityMapper;
import com.pragma.ms_small_square.infrastructure.out.jpa.repository.IOrderRepository;
import com.pragma.ms_small_square.infrastructure.out.jpa.specification.IOrderSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort, IOrderSpecifications {

    private final OrderEntityMapper orderEntityMapper;
    private final IOrderRepository orderRepository;

    @Override
    public Order saveOrder(Order order) {
        OrderEntity orderEntity = orderEntityMapper.toEntity(order);
        if (orderEntity.getDishes() != null) {
            orderEntity.getDishes().forEach(detailEntity -> detailEntity.setOrder(orderEntity));
        }
        return orderEntityMapper.toOrder(orderRepository.save(orderEntity));
    }

    @Override
    public List<Order> getOrderByUserAndStates(Long userId, List<OrderStateEnum> states, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<OrderEntity> specs = Specification
                .where(byUserId(userId))
                .and(byStates(states));

        return orderRepository.findAll(specs, pageable)
                .getContent()
                .stream()
                .map(orderEntityMapper::toOrder)
                .toList();
    }

    @Override
    public Page<Order> getOrdersByState(OrderStateEnum state, int page, int size, Long restaurantId) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<OrderEntity> specs = Specification
                .where(byRestaurantId(restaurantId))
                .and(byStates(List.of(state)));
        return orderRepository.findAll(specs, pageable).map(orderEntityMapper::toOrder);
    }


}
