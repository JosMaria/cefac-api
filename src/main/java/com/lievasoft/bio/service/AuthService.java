package com.lievasoft.bio.service;

import com.lievasoft.bio.controller.LoginRequest;
import com.lievasoft.bio.controller.RegisterRequest;
import com.lievasoft.bio.controller.TokenResponse;

public interface AuthService {

    TokenResponse register(RegisterRequest request);

    TokenResponse login(LoginRequest request);

    TokenResponse refreshToken(String authHeader);
}
