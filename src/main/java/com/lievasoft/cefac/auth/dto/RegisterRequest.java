package com.lievasoft.cefac.auth.dto;

public record RegisterRequest(
        String name,
        String lastname,
        String alias,
        String email,
        String phone
) {
}
