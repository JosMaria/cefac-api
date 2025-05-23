package com.lievasoft.cefac.security;

import com.lievasoft.cefac.auth.JwtService;
import com.lievasoft.cefac.auth.TokenRepository;
import com.lievasoft.cefac.entity.CustomUser;
import com.lievasoft.cefac.user.CustomUserService;
import com.lievasoft.cefac.utils.HelperService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserService customUserService;
    private final HelperService helper;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        boolean isToRefreshToken = request.getServletPath().contains("/auth/refresh-token");
        String authHeader = request.getHeader(AUTHORIZATION);

        if (isToRefreshToken || authHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        helper.obtainBearer(authHeader).ifPresent(token -> {
            if (isValidToken(token)) {
                var username = jwtService.extractUsername(token);
                var user = (CustomUser) customUserService.loadUserByUsername(username);
                if (!user.isDisabled()) {
                    keepAuthentication(user, request);
                }
            }
        });

        filterChain.doFilter(request, response);
    }

    private void keepAuthentication(UserDetails user, HttpServletRequest request) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        WebAuthenticationDetails details = new WebAuthenticationDetailsSource().buildDetails(request);
        authenticationToken.setDetails(details);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private boolean isValidToken(String value) {
        return tokenRepository.findByToken(value)
                .filter(token -> !token.isRevoked())
                .isPresent();
    }
}
