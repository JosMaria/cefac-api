package com.lievasoft.bio.service;

import com.lievasoft.bio.controller.LoginRequest;
import com.lievasoft.bio.controller.RegisterRequest;
import com.lievasoft.bio.controller.TokenResponse;
import com.lievasoft.bio.entity.BioUser;
import com.lievasoft.bio.entity.Token;
import com.lievasoft.bio.repository.BioUserRepository;
import com.lievasoft.bio.repository.TokenRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final BioUserRepository bioUserRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public TokenResponse register(final RegisterRequest request) {
        if (bioUserRepository.existsByUsername(request.username())) {
            var msg = "User with username %s already exists".formatted(request.username());
            log.error(msg);
            throw new EntityExistsException(msg);
        }

        var persistedUser = saveBioUser(request);
        String jwtToken = jwtService.generateToken(persistedUser);
        String refreshToken = jwtService.generateRefreshToken(persistedUser);
        saveBioUserToken(persistedUser, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }

    private BioUser saveBioUser(RegisterRequest request) {
        var bioUserToPersist = BioUser.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();

        return bioUserRepository.save(bioUserToPersist);
    }

    private void saveBioUserToken(BioUser user, String jwtToken) {
        Token tokenToPersist = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .build();

        tokenRepository.save(tokenToPersist);
    }

    @Override
    public TokenResponse login(final LoginRequest request) {
        var authentication = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        Authentication authenticated = authenticationManager.authenticate(authentication);
        String username = ((UserDetails) authenticated.getPrincipal()).getUsername();
        BioUser userObtained = bioUserRepository.findByUsername(username)
                .orElseThrow(() -> {
                    String message = "username %s has not been found.".formatted(username);
                    log.error(message);
                    return new UsernameNotFoundException(message);
                });

        var jwtToken = jwtService.generateToken(userObtained);
        var jwtRefreshToken = jwtService.generateRefreshToken(userObtained);
        saveBioUserToken(userObtained, jwtToken);
        return new TokenResponse(jwtToken, jwtRefreshToken);
    }

    @Override
    public TokenResponse refreshToken(String authHeader) {
        return null;
    }
}
