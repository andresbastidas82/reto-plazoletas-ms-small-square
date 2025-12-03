package com.pragma.ms_small_square.infrastructure.out.jpa.repository;

import com.pragma.ms_small_square.infrastructure.out.jpa.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {
}
