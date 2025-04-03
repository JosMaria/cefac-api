package com.lievasoft.bio.utils;

import com.lievasoft.bio.exception.BearerTokenException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HelperService {

    public Optional<String> getBearerToken(String authHeader) {
        final var prefix = "Bearer ";
        if (authHeader != null && authHeader.startsWith(prefix)) {
            String response = authHeader.replace(prefix, "");
            return Optional.of(response);
        }

        return Optional.empty();
    }

    public String getToken(String authHeader) {
        final var prefix = "Bearer ";
        if (authHeader != null && authHeader.startsWith(prefix)) {
            return authHeader.replace(prefix, "");

        } else {
            throw new BearerTokenException();
        }
    }
}
