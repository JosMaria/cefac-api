package com.lievasoft.bio.service;

import com.lievasoft.bio.dto.LoginRequest;
import com.lievasoft.bio.dto.RegisterRequest;
import com.lievasoft.bio.dto.TokenResponse;

public interface AuthService {

    TokenResponse register(RegisterRequest request);

    TokenResponse login(LoginRequest request);

    TokenResponse refreshToken(String authHeader);
}
