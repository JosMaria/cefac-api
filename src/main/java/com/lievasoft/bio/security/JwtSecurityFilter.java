package com.lievasoft.bio.security;

import com.lievasoft.bio.auth.JwtService;
import com.lievasoft.bio.auth.TokenRepository;
import com.lievasoft.bio.entity.CustomUser;
import com.lievasoft.bio.entity.Token;
import com.lievasoft.bio.user.CustomUserService;
import com.lievasoft.bio.utils.HelperService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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

        var optionalValueToken = helper.getBearerToken(authHeader);
        if (optionalValueToken.isPresent()) {
            String valueToken = optionalValueToken.get();

            if (isTokenValid(valueToken)) {
                var username = jwtService.extractUsername(valueToken);
                var user = (CustomUser) customUserService.loadUserByUsername(username);
                var authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                WebAuthenticationDetails details = new WebAuthenticationDetailsSource().buildDetails(request);
                authenticationToken.setDetails(details);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    public boolean isTokenValid(String value) {
        boolean isValid = false;
        var optionalToken = tokenRepository.findByToken(value);
        if (optionalToken.isPresent()) {
            Token obtainedToken = optionalToken.get();
            isValid = !obtainedToken.isRevoked();
        }

        return isValid;
    }
}
