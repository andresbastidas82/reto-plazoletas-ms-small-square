package com.pragma.ms_small_square.infrastructure.configuration;

import com.pragma.ms_small_square.domain.api.IRestaurantServicePort;
import com.pragma.ms_small_square.domain.spi.IAuthenticationServicePort;
import com.pragma.ms_small_square.domain.spi.IRestaurantPersistencePort;
import com.pragma.ms_small_square.domain.usecase.RestaurantUseCase;
import com.pragma.ms_small_square.infrastructure.out.jpa.adapter.RestaurantJpaAdapter;
import com.pragma.ms_small_square.infrastructure.out.jpa.mapper.RestaurantEntityMapper;
import com.pragma.ms_small_square.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantJpaAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort(IRestaurantPersistencePort restaurantPersistencePort,
                                                        IAuthenticationServicePort authenticationServicePort) {
        return new RestaurantUseCase(restaurantPersistencePort, authenticationServicePort);
    }

}
