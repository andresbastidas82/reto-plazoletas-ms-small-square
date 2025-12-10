package com.pragma.ms_small_square.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsRequest {

    @NotNull(message = "The dish id is required")
    private Long dishId;

    @NotNull(message = "The quantity is required")
    private Integer quantity;
}
