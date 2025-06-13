package com.lievasoft.cefac.dto.user;

import com.lievasoft.cefac.entity.user.Role;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String name,
        String lastname,
        String email,
        String phone,
        Role role
) {
}
