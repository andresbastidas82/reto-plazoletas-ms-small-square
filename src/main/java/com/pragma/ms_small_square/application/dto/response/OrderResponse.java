package com.pragma.ms_small_square.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderResponse {
    private Long id;
    private String state;
    private String customer;
    private String restaurant;
    private String employee;
    private LocalDateTime creationDate;
    private List<OrderDetailsResponse> dishes;
}
