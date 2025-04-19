package com.lievasoft.cefac.utils;

import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class HelperService {

    public Optional<String> obtainBearer(String authHeader) {
        final var prefix = "Bearer ";

        if (Objects.nonNull(authHeader) && authHeader.startsWith(prefix)) {
            String value = authHeader.replace(prefix, "");
            return Optional.of(value);

        } else {
            return Optional.empty();
        }
    }
}
