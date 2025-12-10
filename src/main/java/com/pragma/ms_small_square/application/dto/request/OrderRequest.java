package com.pragma.ms_small_square.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    @NotNull(message = "The restaurant id is required")
    private Long restaurantId;

    @NotEmpty(message = "The dishes are required")
    @Valid
    private List<OrderDetailsRequest> dishes;
}
