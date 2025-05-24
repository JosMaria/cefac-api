package com.lievasoft.cefac.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UsernameUpdateRequest(
        @NotBlank(message = "Username value invalid is blank.")
        String username
) {
}
