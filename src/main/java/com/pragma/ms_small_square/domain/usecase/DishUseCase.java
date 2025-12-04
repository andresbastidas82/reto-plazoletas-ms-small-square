package com.pragma.ms_small_square.domain.usecase;

import com.pragma.ms_small_square.domain.api.IDishServicePort;
import com.pragma.ms_small_square.domain.exception.DishNotFoundException;
import com.pragma.ms_small_square.domain.exception.UserNotOwnerException;
import com.pragma.ms_small_square.domain.model.Dish;
import com.pragma.ms_small_square.domain.spi.IDishPersistencePort;
import com.pragma.ms_small_square.domain.spi.IUserClientPort;

public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final IUserClientPort userClientPort;

    public DishUseCase(IDishPersistencePort dishPersistencePort, IUserClientPort userClientPort) {
        this.dishPersistencePort = dishPersistencePort;
        this.userClientPort = userClientPort;
    }

    @Override
    public Dish saveDish(Dish dish) {
        return dishPersistencePort.saveDish(dish);
    }

    @Override
    public Dish getDishById(Long id) {
        Dish dish = dishPersistencePort.getDishById(id);
        if (dish == null) {
            throw new DishNotFoundException();
        }
        return dish;
    }
}
