package com.pragma.ms_small_square.infrastructure.configuration;

import com.pragma.ms_small_square.domain.api.IDishServicePort;
import com.pragma.ms_small_square.domain.api.IRestaurantServicePort;
import com.pragma.ms_small_square.domain.spi.IDishPersistencePort;
import com.pragma.ms_small_square.domain.spi.IRestaurantPersistencePort;
import com.pragma.ms_small_square.domain.spi.IUserClientPort;
import com.pragma.ms_small_square.domain.usecase.DishUseCase;
import com.pragma.ms_small_square.domain.usecase.RestaurantUseCase;
import com.pragma.ms_small_square.infrastructure.out.jpa.adapter.DishJpaAdapter;
import com.pragma.ms_small_square.infrastructure.out.jpa.adapter.RestaurantJpaAdapter;
import com.pragma.ms_small_square.infrastructure.out.jpa.mapper.DishEntityMapper;
import com.pragma.ms_small_square.infrastructure.out.jpa.mapper.RestaurantEntityMapper;
import com.pragma.ms_small_square.infrastructure.out.jpa.repository.IDishRepository;
import com.pragma.ms_small_square.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;
    private final IDishRepository dishRepository;
    private final DishEntityMapper dishEntityMapper;

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantJpaAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort(IRestaurantPersistencePort restaurantPersistencePort,
                                                        IUserClientPort userClientPort) {
        return new RestaurantUseCase(restaurantPersistencePort, userClientPort);
    }

    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishJpaAdapter(dishRepository, dishEntityMapper);
    }

    @Bean
    public IDishServicePort dishServicePort(IDishPersistencePort dishPersistencePort,
                                            IUserClientPort userClientPort) {
        return new DishUseCase(dishPersistencePort, userClientPort);
    }
}
