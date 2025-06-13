package com.lievasoft.cefac.controller;

import com.lievasoft.cefac.dto.auth.LoginRequestDto;
import com.lievasoft.cefac.dto.auth.RegisterRequestDto;
import com.lievasoft.cefac.dto.auth.TokenResponseDto;
import com.lievasoft.cefac.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<TokenResponseDto> register(@RequestBody final RegisterRequestDto request) {
        var tokenResponse = service.register(request);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> authenticate(@Valid @RequestBody final LoginRequestDto request) {
        var tokenResponse = service.login(request);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/refresh-token")
    public TokenResponseDto refreshToken(@RequestHeader(value = AUTHORIZATION) final String authHeader) {
        return service.refreshToken(authHeader);
    }
}
