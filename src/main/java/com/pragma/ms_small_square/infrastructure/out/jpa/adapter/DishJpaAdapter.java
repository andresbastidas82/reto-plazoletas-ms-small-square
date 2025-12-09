package com.pragma.ms_small_square.infrastructure.out.jpa.adapter;

import com.pragma.ms_small_square.domain.model.Dish;
import com.pragma.ms_small_square.domain.model.enums.DishCategoryEnum;
import com.pragma.ms_small_square.domain.spi.IDishPersistencePort;
import com.pragma.ms_small_square.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.ms_small_square.infrastructure.out.jpa.mapper.DishEntityMapper;
import com.pragma.ms_small_square.infrastructure.out.jpa.repository.IDishRepository;
import com.pragma.ms_small_square.infrastructure.out.jpa.specification.IDishSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort, IDishSpecifications {

    private final IDishRepository dishRepository;
    private final DishEntityMapper dishEntityMapper;

    @Override
    public Dish saveDish(Dish dish) {
        DishEntity dishEntity = dishRepository.save(dishEntityMapper.toEntity(dish));
        return dishEntityMapper.toDish(dishEntity);
    }

    @Override
    public Dish getDishById(Long id) {
        return dishRepository.findById(id)
                .map(dishEntityMapper::toDish)
                .orElse(null);
    }

    @Override
    public Page<Dish> getDishesByRestaurant(Long restaurantId, DishCategoryEnum category, int page, int size) {
        // 1. Crear el objeto Pageable para la paginación
        Pageable pageable = PageRequest.of(page, size);

        // 2. Construir la especificación combinando los filtros
        //    Specification.where() maneja elegantemente los filtros nulos.
        Specification<DishEntity> spec = Specification
                .where(isActive())
                .and(byRestaurantId(restaurantId))
                .and(byCategory(category));

        // 3. Ejecutar la consulta usando el repositorio
        Page<DishEntity> dishEntityPage = dishRepository.findAll(spec, pageable);

        // 4. Mapear el resultado al modelo de dominio
        return dishEntityPage.map(dishEntityMapper::toDish);
    }

}
