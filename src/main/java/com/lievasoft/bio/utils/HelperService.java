package com.lievasoft.bio.utils;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HelperService {

    public Optional<String> getValueBearerToken(String authHeader) {
        final var prefix = "Bearer ";
        if (authHeader != null && authHeader.startsWith(prefix)) {
            String response = authHeader.replace(prefix, "");
            return Optional.of(response);
        }

        return Optional.empty();
    }
}
