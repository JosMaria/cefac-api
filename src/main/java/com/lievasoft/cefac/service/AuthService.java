package com.lievasoft.cefac.service;

import com.lievasoft.cefac.dto.auth.LoginRequestDto;
import com.lievasoft.cefac.dto.auth.RegisterRequestDto;
import com.lievasoft.cefac.dto.auth.TokenResponseDto;

public interface AuthService {

    TokenResponseDto register(RegisterRequestDto request);

    TokenResponseDto login(LoginRequestDto request);

    TokenResponseDto refreshToken(String authHeader);
}
