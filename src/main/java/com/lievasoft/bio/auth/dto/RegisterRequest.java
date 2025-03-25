package com.lievasoft.bio.auth.dto;

public record RegisterRequest(
        String username,
        String password
) {
}
