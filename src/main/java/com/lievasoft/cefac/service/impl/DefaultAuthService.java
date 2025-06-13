package com.lievasoft.cefac.service.impl;

import com.lievasoft.cefac.repository.TokenRepository;
import com.lievasoft.cefac.dto.auth.LoginRequestDto;
import com.lievasoft.cefac.dto.auth.RegisterRequestDto;
import com.lievasoft.cefac.dto.auth.TokenResponseDto;
import com.lievasoft.cefac.entity.user.CustomUser;
import com.lievasoft.cefac.entity.Token;
import com.lievasoft.cefac.exception.BearerTokenException;
import com.lievasoft.cefac.exception.types.AlreadyExistsException;
import com.lievasoft.cefac.service.AuthService;
import com.lievasoft.cefac.mapper.CustomUserMapper;
import com.lievasoft.cefac.repository.CustomUserRepository;
import com.lievasoft.cefac.utils.HelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.lievasoft.cefac.exception.Problem.REGISTERED_EMAIL;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserRepository customUserRepository;
    private final CustomUserMapper customUserMapper;
    private final TokenRepository tokenRepository;
    private final DefaultJwtService jwtService;
    private final HelperService helper;

    @Override
    public TokenResponseDto register(final RegisterRequestDto payload) {
        customUserRepository
                .findByEmail(payload.email())
                .ifPresent(customUser -> {
                    String msg = "User with email %s already exists.".formatted(customUser.getEmail());
                    throw new AlreadyExistsException(msg, REGISTERED_EMAIL);
                });

        var customUserToPersist = customUserMapper.mapToCustomUser(payload);
        var persistedCustomUser = customUserRepository.save(customUserToPersist);
        return generateTokens(persistedCustomUser);
    }

    @Override
    public TokenResponseDto login(final LoginRequestDto request) {
        var authentication = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        Authentication authenticated = authenticationManager.authenticate(authentication);
        var obtainedCustomUser = (CustomUser) authenticated.getPrincipal();
        return generateTokens(obtainedCustomUser);
    }

    @Override
    public TokenResponseDto refreshToken(final String authHeader) {
        var refreshToken = helper.obtainBearer(authHeader).orElseThrow(BearerTokenException::new);
        var username = jwtService.extractUsername(refreshToken);
        var obtainedCustomUser = customUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        var countUpdatedTokens = tokenRepository.revokeTokensByUserId(obtainedCustomUser.getId());
        log.info("The count of updated tokens is {}", countUpdatedTokens);
        return generateTokens(obtainedCustomUser);
    }

    private TokenResponseDto generateTokens(CustomUser user) {
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        Token tokenToPersist = createTokenEntity(user, jwtToken);
        tokenRepository.save(tokenToPersist);
        return new TokenResponseDto(jwtToken, refreshToken);
    }

    private Token createTokenEntity(CustomUser user, String jwtToken) {
        return Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .build();
    }
}
