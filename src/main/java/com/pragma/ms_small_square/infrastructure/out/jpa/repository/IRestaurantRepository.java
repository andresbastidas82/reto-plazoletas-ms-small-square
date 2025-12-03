package com.pragma.ms_small_square.infrastructure.out.jpa.repository;

import com.pragma.ms_small_square.infrastructure.out.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
}
