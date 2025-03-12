package com.lievasoft.bio.security;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtSecurityFilter extends OncePerRequestFilter {

    private final String SECRET_KEY = "tu-clave-secret-muy-segura-y-long-para-jwt";
    private final String PREFIX_TOKEN = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith(PREFIX_TOKEN)) {
             filterChain.doFilter(request, response);
             return;
        }

        String token = header.replace(PREFIX_TOKEN, "");
        try {

        }



        filterChain.doFilter(request, response);
    }
}
