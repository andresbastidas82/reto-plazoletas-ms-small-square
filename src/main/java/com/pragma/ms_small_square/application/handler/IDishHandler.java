package com.pragma.ms_small_square.application.handler;

import com.pragma.ms_small_square.application.dto.request.DishRequest;
import com.pragma.ms_small_square.application.dto.response.DishResponse;
import com.pragma.ms_small_square.application.dto.request.DishUpdateRequest;
import org.springframework.data.domain.Page;

public interface IDishHandler {
    DishResponse saveDish(DishRequest dishRequest);
    DishResponse updateDish(Long id, DishUpdateRequest dishUpdateRequest);
    DishResponse updateStateDish(Long id, Boolean state);
    Page<DishResponse> getDishesByRestaurant(Long restaurantId, String category, int page, int size);
}
