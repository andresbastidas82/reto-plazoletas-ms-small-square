package com.pragma.ms_small_square.application.mapper;

import com.pragma.ms_small_square.application.dto.request.RestaurantRequest;
import com.pragma.ms_small_square.application.dto.response.RestaurantResponse;
import com.pragma.ms_small_square.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestaurantRequestMapper {

    Restaurant toRestaurant(RestaurantRequest restaurantRequest);

    RestaurantResponse toRestaurantResponse(Restaurant restaurant);

}
