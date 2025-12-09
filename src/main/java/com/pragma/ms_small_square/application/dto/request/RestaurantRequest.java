package com.pragma.ms_small_square.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRequest {

    @NotBlank(message = "The name is required")
    @Pattern(regexp = "^(?=.*[a-zA-Z]).*[a-zA-Z0-9 ]+$", message = "The restaurant name cannot contain only numbers")
    private String name;

    @NotBlank(message = "The NIT is required")
    @Pattern(regexp = "^[0-9]+$", message = "The NIT must contain only numbers")
    private String nit;

    @NotBlank(message = "The address is required")
    private String address;

    @NotBlank(message = "The phone is required")
    @Size(max = 13, message = "The phone number cannot exceed 13 characters.")
    @Pattern(regexp = "^\\+?[0-9]+$", message = "The phone number should contain only numbers and optionally the '+' symbol at the beginning")
    private String phone;

    @NotBlank(message = "The logo URL is required")
    private String urlLogo;

    @NotNull(message = "The owner user ID is required")
    private Long ownerId;

}
