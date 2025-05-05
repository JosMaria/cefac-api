package com.lievasoft.cefac.auth.dto;

import com.lievasoft.cefac.entity.Role;

public record RegisterRequest(
        String name,
        String lastname,
        String email,
        String phone,
        Role role
) {
}
