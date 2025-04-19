package com.lievasoft.cefac.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "username field should not be null, empty or blank")
        String username,
        @NotBlank(message = "password field should not be null, empty or blank")
        String password
) {
}
