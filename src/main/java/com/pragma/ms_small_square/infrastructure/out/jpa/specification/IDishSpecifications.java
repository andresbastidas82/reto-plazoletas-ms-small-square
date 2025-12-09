package com.pragma.ms_small_square.infrastructure.out.jpa.specification;

import com.pragma.ms_small_square.domain.model.enums.DishCategoryEnum;
import com.pragma.ms_small_square.infrastructure.out.jpa.entity.DishEntity;
import org.springframework.data.jpa.domain.Specification;

public interface IDishSpecifications {

    default Specification<DishEntity> byRestaurantId(Long restaurantId) {
        return (root, query, cb) -> restaurantId == null
                ? cb.conjunction()
                : cb.equal(root.join("restaurant").get("id"), restaurantId);
    }

    /**
     * Devuelve una especificación para filtrar por categoría, solo si la categoría no es nula o vacía.
     */
    default Specification<DishEntity> byCategory(DishCategoryEnum category) {
        return (root, query, cb) ->
                category == null
                        ? cb.conjunction()
                        : cb.equal(root.get("category"), category);
    }

    /**
     * Devuelve una especificación para filtrar solo los platos activos.
     */
    default Specification<DishEntity> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("state"));
    }
}
