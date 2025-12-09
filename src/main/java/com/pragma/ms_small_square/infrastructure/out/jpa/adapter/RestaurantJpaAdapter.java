package com.pragma.ms_small_square.infrastructure.out.jpa.adapter;

import com.pragma.ms_small_square.domain.model.Restaurant;
import com.pragma.ms_small_square.domain.spi.IRestaurantPersistencePort;
import com.pragma.ms_small_square.infrastructure.out.jpa.dto.RestaurantSummaryDto;
import com.pragma.ms_small_square.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.ms_small_square.infrastructure.out.jpa.mapper.RestaurantEntityMapper;
import com.pragma.ms_small_square.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        RestaurantEntity restaurantEntity = restaurantEntityMapper.toRestaurantEntity(restaurant);
        return restaurantEntityMapper.toRestaurant(restaurantRepository.save(restaurantEntity));
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurantEntityMapper::toRestaurant)
                .orElse(null);
    }

    @Override
    public Page<Restaurant> getRestaurants(Integer page, Integer size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        // El repositorio nos devuelve una página de entidades JPA.
        Page<RestaurantSummaryDto> summaryPage = restaurantRepository.findRestaurantSummaries(pageable);
        // Mapeamos la página de entidades a una página de modelos de dominio.
        return summaryPage.map(item -> Restaurant.builder().id(item.getId()).name(item.getName()).urlLogo(item.getUrlLogo()).build());
    }
}
