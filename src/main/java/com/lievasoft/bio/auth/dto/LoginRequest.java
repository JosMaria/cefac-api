package com.lievasoft.bio.auth.dto;

public record LoginRequest(
        String username,
        String password
) {
}
