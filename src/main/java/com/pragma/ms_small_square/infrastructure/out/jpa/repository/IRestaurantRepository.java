package com.pragma.ms_small_square.infrastructure.out.jpa.repository;

import com.pragma.ms_small_square.infrastructure.out.jpa.dto.RestaurantSummaryDto;
import com.pragma.ms_small_square.infrastructure.out.jpa.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IRestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    @Query("SELECT NEW com.pragma.ms_small_square.infrastructure.out.jpa.dto.RestaurantSummaryDto(r.id, r.name, r.urlLogo) " +
            "FROM RestaurantEntity r")
    Page<RestaurantSummaryDto> findRestaurantSummaries(Pageable pageable);

}
