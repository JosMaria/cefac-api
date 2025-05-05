package com.lievasoft.cefac.user.dto;

import com.lievasoft.cefac.entity.Role;

public record UserResponseDto(
        String name,
        String lastname,
        String email,
        String phone,
        Role role
) {
}
