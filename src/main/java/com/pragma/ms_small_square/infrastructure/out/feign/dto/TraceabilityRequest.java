package com.pragma.ms_small_square.infrastructure.out.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TraceabilityRequest {
    private Long orderId;
    private Long employeeId;
    private Long customerId;
    private String previousState;
    private String newState;
    private LocalDateTime dateNewState;
}
