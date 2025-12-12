package com.pragma.ms_small_square.infrastructure.out.feign.adapter;

import com.pragma.ms_small_square.domain.model.Order;
import com.pragma.ms_small_square.domain.spi.ITraceabilityClientPort;
import com.pragma.ms_small_square.infrastructure.out.feign.ITraceabilityFeignClient;
import com.pragma.ms_small_square.infrastructure.out.feign.dto.ResponseTraceability;
import com.pragma.ms_small_square.infrastructure.out.feign.dto.TraceabilityRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
@Slf4j
public class TraceabilityClientAdapter implements ITraceabilityClientPort {
    private final ITraceabilityFeignClient traceabilityFeignClient;


    @Override
    public Boolean saveTraceability(Order order, String previousState, String newState) {
        TraceabilityRequest traceabilityRequest = TraceabilityRequest.builder()
                .dateNewState(LocalDateTime.now())
                .employeeId(order.getEmployee() != null ? order.getEmployee().getId() : null)
                .customerId(order.getUser().getId())
                .orderId(order.getId())
                .previousState(previousState)
                .newState(newState)
                .build();
        try {
            ResponseTraceability response = traceabilityFeignClient.saveTraceability(traceabilityRequest);
            if (response != null && response.getHttpStatus() == 201) {
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            log.error("Error saving traceability: {}", e.getMessage());
        }
        return Boolean.FALSE;
    }
}
