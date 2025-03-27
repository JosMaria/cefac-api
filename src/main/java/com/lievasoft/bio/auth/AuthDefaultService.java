package com.lievasoft.bio.auth;

import com.lievasoft.bio.auth.dto.LoginRequest;
import com.lievasoft.bio.auth.dto.RegisterRequest;
import com.lievasoft.bio.auth.dto.TokenResponse;
import com.lievasoft.bio.entity.CustomUser;
import com.lievasoft.bio.entity.Token;
import com.lievasoft.bio.exception.BearerTokenException;
import com.lievasoft.bio.user.CustomUserMapper;
import com.lievasoft.bio.user.CustomUserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthDefaultService implements AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserMapper customUserMapper;
    private final CustomUserRepository customUserRepository;
    private final TokenRepository tokenRepository;

    @Override
    public TokenResponse register(final RegisterRequest payload) {
        throwExceptionIfExistsCustomUser(payload.username());
        var customUserToPersist = customUserMapper.map(payload);
        var persistedCustomUser = customUserRepository.save(customUserToPersist);
        return generateTokens(persistedCustomUser);
    }

    @Override
    public TokenResponse login(final LoginRequest request) {
        var authentication = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        Authentication authenticated = authenticationManager.authenticate(authentication);
        var obtainedCustomUser = (CustomUser) authenticated.getPrincipal();
        return generateTokens(obtainedCustomUser);
    }

    @Override
    public TokenResponse refreshToken(final String authHeader) {
        var refreshToken = getValueBearerToken(authHeader).orElseThrow(BearerTokenException::new);
        var username = jwtService.extractUsername(refreshToken);
        var obtainedCustomUser = customUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return generateTokens(obtainedCustomUser);
    }

    private Optional<String> getValueBearerToken(String authHeader) {
        final var prefix = "Bearer ";
        if (authHeader != null && authHeader.startsWith(prefix)) {
            String response = authHeader.replace(prefix, "");
            return Optional.of(response);
        }

        return Optional.empty();
    }

    private void throwExceptionIfExistsCustomUser(String username) {
        if (customUserRepository.existsByUsername(username)) {
            throw new EntityExistsException("User with username %s already exists".formatted(username));
        }
    }

    private TokenResponse generateTokens(CustomUser user) {
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        Token tokenToPersist = createTokenToPersist(user, jwtToken);
        tokenRepository.save(tokenToPersist);
        return new TokenResponse(jwtToken, refreshToken);
    }

    private Token createTokenToPersist(CustomUser user, String jwtToken) {
        return Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .build();
    }
}
