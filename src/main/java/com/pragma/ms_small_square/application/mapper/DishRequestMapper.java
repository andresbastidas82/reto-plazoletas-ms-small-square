package com.pragma.ms_small_square.application.mapper;

import com.pragma.ms_small_square.application.dto.DishRequest;
import com.pragma.ms_small_square.application.dto.DishResponse;
import com.pragma.ms_small_square.domain.model.Dish;
import com.pragma.ms_small_square.domain.model.enums.DishCategoryEnum;
import com.pragma.ms_small_square.infrastructure.exception.DishCategoryNotFounException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface DishRequestMapper {

    @Mapping(target = "category", qualifiedByName = "getCategoyEnum")
    Dish toDish(DishRequest dishRequest);

    @Mapping(target = "restaurant", source = "restaurant.name")
    @Mapping(target = "category", qualifiedByName = "showCategoyEnum")
    @Mapping(target = "state", qualifiedByName = "getDishState")
    DishResponse toDishResponse(Dish dish);

    @Named("getDishState")
    default String getDishState(Boolean state) {
        return Boolean.TRUE.equals(state) ? "ACTIVE" : "INACTIVE";
    }

    @Named("showCategoyEnum")
    default String showCategoyEnum(DishCategoryEnum category) {
        return category.name();
    }

    @Named("getCategoyEnum")
    default DishCategoryEnum getCategoryEnum(String category) {
        try{
            return DishCategoryEnum.valueOf(category);
        } catch (Exception e) {
            throw new DishCategoryNotFounException();
        }
    }
}
