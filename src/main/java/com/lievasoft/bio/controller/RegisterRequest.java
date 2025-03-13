package com.lievasoft.bio.controller;

public record RegisterRequest(
        String email,
        String password,
        String name
) {
}
