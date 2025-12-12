package com.pragma.ms_small_square.domain.spi;

import com.pragma.ms_small_square.domain.model.Order;

public interface ITraceabilityClientPort {

    Boolean saveTraceability(Order order, String previousState, String newState);
}
