package com.lievasoft.cefac.security;

import com.lievasoft.cefac.auth.TokenRepository;
import com.lievasoft.cefac.exception.BearerTokenException;
import com.lievasoft.cefac.utils.HelperService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final HelperService helper;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        var authHeader = request.getHeader(AUTHORIZATION);
        var token = helper.obtainBearer(authHeader).orElseThrow(BearerTokenException::new);
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        var obtainedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Token not found"));
        obtainedToken.setRevoked(true);
        tokenRepository.save(obtainedToken);
    }
}
