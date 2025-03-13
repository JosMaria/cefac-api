package com.lievasoft.bio.service;

import com.lievasoft.bio.controller.LoginRequest;
import com.lievasoft.bio.controller.RegisterRequest;
import com.lievasoft.bio.controller.TokenResponse;
import com.lievasoft.bio.entity.Token;
import com.lievasoft.bio.entity.User;
import com.lievasoft.bio.repository.TokenRepository;
import com.lievasoft.bio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public TokenResponse register(RegisterRequest request) {
        User persistedUser = saveUser(request);
//        String jwtToken = jwtService.generateToken(persistedUser);
//        String refreshToken = jwtService.generateRefreshToken(persistedUser);
//        saveUserToken(persistedUser, jwtToken);
//        return new TokenResponse(jwtToken, refreshToken);
        return null;
    }

    private User saveUser(RegisterRequest request) {
        var userToPersist = User.builder()
                .email(request.email())
                .name(request.name())
                .password(passwordEncoder.encode(request.password()))
                .build();

        return userRepository.save(userToPersist);
    }

    private void saveUserToken(User user, String jwtToken) {
        Token tokenToPersist = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .build();

        tokenRepository.save(tokenToPersist);
    }

    public TokenResponse login(LoginRequest request) {
        return null;
    }

    public TokenResponse refreshToken(String authHeader) {
        return null;
    }
}
