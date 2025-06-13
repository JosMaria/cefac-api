package com.lievasoft.cefac.dto.auth;

import com.lievasoft.cefac.entity.user.Role;

public record RegisterRequestDto(
        String name,
        String lastname,
        String email,
        Role role
) {
}
