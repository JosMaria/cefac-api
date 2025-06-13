package com.lievasoft.cefac.controller;

import com.lievasoft.cefac.service.AuthService;
import com.lievasoft.cefac.auth.dto.LoginRequest;
import com.lievasoft.cefac.auth.dto.RegisterRequest;
import com.lievasoft.cefac.auth.dto.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody final RegisterRequest request) {
        var tokenResponse = service.register(request);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(@Valid @RequestBody final LoginRequest request) {
        var tokenResponse = service.login(request);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/refresh-token")
    public TokenResponse refreshToken(@RequestHeader(value = AUTHORIZATION) final String authHeader) {
        return service.refreshToken(authHeader);
    }
}
