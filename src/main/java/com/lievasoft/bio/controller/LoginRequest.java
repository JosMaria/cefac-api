package com.lievasoft.bio.controller;

public record LoginRequest(
        String email,
        String username,
        String password
) {
}
