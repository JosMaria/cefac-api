package com.lievasoft.bio.auth;

import com.lievasoft.bio.auth.dto.LoginRequest;
import com.lievasoft.bio.auth.dto.RegisterRequest;
import com.lievasoft.bio.auth.dto.TokenResponse;

public interface AuthService {

    TokenResponse register(RegisterRequest request);

    TokenResponse login(LoginRequest request);

    TokenResponse refreshToken(String authHeader);
}
