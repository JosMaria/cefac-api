package com.lievasoft.bio.auth;

import com.lievasoft.bio.auth.dto.LoginRequest;
import com.lievasoft.bio.auth.dto.RegisterRequest;
import com.lievasoft.bio.auth.dto.TokenResponse;
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
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody final RegisterRequest request) {
        var response = service.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(@Valid @RequestBody final LoginRequest request) {
        TokenResponse response = service.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public TokenResponse refreshToken(@RequestHeader(value = AUTHORIZATION) final String authHeader) {
        return service.refreshToken(authHeader);
    }
}
