package com.pragma.ms_small_square.infrastructure.out.jpa.mapper;

import com.pragma.ms_small_square.domain.model.Order;
import com.pragma.ms_small_square.infrastructure.out.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderEntityMapper {

    OrderEntity toEntity(Order order);

    Order toOrder(OrderEntity orderEntity);
}
