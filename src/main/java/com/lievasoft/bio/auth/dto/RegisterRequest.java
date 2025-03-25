package com.lievasoft.bio.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank(message = "username field should not be null, empty or blank")
        String username,
        @NotBlank(message = "password field should not be null, empty or blank")
        String password
) {
}
