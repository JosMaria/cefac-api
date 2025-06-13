package com.lievasoft.cefac.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "username field should not be null, empty or blank")
        String username,
        @NotBlank(message = "password field should not be null, empty or blank")
        String password
) {
}
