package com.pragma.ms_small_square.application.mapper;

import com.pragma.ms_small_square.application.dto.response.OrderDetailsResponse;
import com.pragma.ms_small_square.application.dto.response.OrderResponse;
import com.pragma.ms_small_square.domain.model.Dish;
import com.pragma.ms_small_square.domain.model.Order;
import com.pragma.ms_small_square.domain.model.OrderDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderRequestMapper {

    // Mapeo principal de la Orden
    @Mapping(target = "customer", source = "user.name")
    @Mapping(target = "restaurant", source = "restaurant.name")
    @Mapping(target = "dishes", source = "dishes") // Mapea la lista de detalles
    OrderResponse toOrderResponse(Order order);

    // Mapeo para la lista de detalles
    // MapStruct usará automáticamente el metodo de abajo para cada elemento de la lista
    List<OrderDetailsResponse> toOrderDetailResponseList(List<OrderDetails> orderDetails);

    // Mapeo para un solo detalle de la orden
    @Mapping(target = "dishId", source = "dish.id")
    @Mapping(target = "name", source = "dish.name")
    @Mapping(target = "category", source = "dish.category")
    @Mapping(target = "description", source = "dish.description")
    OrderDetailsResponse toOrderDetailResponse(OrderDetails orderDetail);

    // --- MÉTODOS PERSONALIZADOS ---

    @Named("mapDishToName")
    default String mapDishToName(Dish dish) {
        return dish != null ? dish.getName() : null;
    }
}
