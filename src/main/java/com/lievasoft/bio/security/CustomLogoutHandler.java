package com.lievasoft.bio.security;

import com.lievasoft.bio.auth.TokenRepository;
import com.lievasoft.bio.entity.Token;
import com.lievasoft.bio.utils.HelperService;
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
        var auth = request.getHeader(AUTHORIZATION);
        var token = helper.getToken(auth);

        Token obtainedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Token not found"));
        obtainedToken.setRevoked(true);
        tokenRepository.save(obtainedToken);
    }
}
