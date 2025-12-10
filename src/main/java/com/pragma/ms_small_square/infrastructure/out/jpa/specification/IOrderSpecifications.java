package com.pragma.ms_small_square.infrastructure.out.jpa.specification;

import com.pragma.ms_small_square.domain.model.enums.OrderStateEnum;
import com.pragma.ms_small_square.infrastructure.out.jpa.entity.OrderEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IOrderSpecifications {

    default Specification<OrderEntity> byUserId(Long userId) {
        return (root, query, cb) -> userId == null
                ? cb.conjunction()
                : cb.equal(root.join("user").get("id"), userId);
    }

    default Specification<OrderEntity> byRestaurantId(Long restaurantId) {
        return (root, query, cb) -> restaurantId == null
                ? cb.conjunction()
                : cb.equal(root.join("restaurant").get("id"), restaurantId);
    }

    default Specification<OrderEntity> byStates(List<OrderStateEnum> states) {
        return (root, query, cb) -> states == null || states.isEmpty()
                ? cb.conjunction()
                : root.get("state").in(states);
    }
}
