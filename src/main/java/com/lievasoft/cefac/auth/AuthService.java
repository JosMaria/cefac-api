package com.lievasoft.cefac.auth;

import com.lievasoft.cefac.auth.dto.LoginRequest;
import com.lievasoft.cefac.auth.dto.RegisterRequest;
import com.lievasoft.cefac.auth.dto.TokenResponse;

public interface AuthService {

    TokenResponse register(RegisterRequest request);

    TokenResponse login(LoginRequest request);

    TokenResponse refreshToken(String authHeader);
}
