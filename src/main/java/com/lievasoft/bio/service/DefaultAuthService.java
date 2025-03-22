package com.lievasoft.bio.service;

import com.lievasoft.bio.controller.LoginRequest;
import com.lievasoft.bio.controller.RegisterRequest;
import com.lievasoft.bio.controller.TokenResponse;
import com.lievasoft.bio.entity.Token;
import com.lievasoft.bio.entity.User;
import com.lievasoft.bio.repository.TokenRepository;
import com.lievasoft.bio.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public TokenResponse register(final RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            String message = String.format("User with email %s already exists", request.email());
            log.warn(message);
            throw new EntityExistsException(message);
        }

        User persistedUser = saveUser(request);
        String jwtToken = jwtService.generateToken(persistedUser);
        String refreshToken = jwtService.generateRefreshToken(persistedUser);
        saveUserToken(persistedUser, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }

    private User saveUser(RegisterRequest request) {
        var userToPersist = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
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

    @Override
    public TokenResponse login(final LoginRequest request) {
        var authentication = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authenticated = authenticationManager.authenticate(authentication);
        var principal = (UserDetails) authenticated.getPrincipal();
        System.out.println(principal.getUsername());

        return null;
    }

    @Override
    public TokenResponse refreshToken(String authHeader) {
        return null;
    }
}
