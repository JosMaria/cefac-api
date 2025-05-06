package com.lievasoft.cefac.user.dto;

import com.lievasoft.cefac.entity.Role;

public record UserResponseDto(
        Long id,
        String name,
        String lastname,
        String email,
        String phone,
        Role role
) {
}
