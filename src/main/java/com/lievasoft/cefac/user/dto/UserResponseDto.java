package com.lievasoft.cefac.user.dto;

import com.lievasoft.cefac.entity.Role;

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
