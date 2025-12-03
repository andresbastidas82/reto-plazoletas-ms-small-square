package com.pragma.ms_small_square.application.handler;

import com.pragma.ms_small_square.application.dto.DishRequest;
import com.pragma.ms_small_square.application.dto.DishResponse;

public interface IDishHandler {

    DishResponse saveDish(DishRequest dishRequest);
}
