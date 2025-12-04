package com.pragma.ms_small_square.infrastructure.out.jpa.adapter;

import com.pragma.ms_small_square.domain.exception.RestaurantNotFoundException;
import com.pragma.ms_small_square.domain.model.Dish;
import com.pragma.ms_small_square.domain.spi.IDishPersistencePort;
import com.pragma.ms_small_square.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.ms_small_square.infrastructure.out.jpa.mapper.DishEntityMapper;
import com.pragma.ms_small_square.infrastructure.out.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

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

}
