package com.lievasoft.cefac.auth.dto;

public record RegisterRequest(
        String username,
        String name,
        String lastname,
        String alias,
        String email,
        String phone
) {
}
