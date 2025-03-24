package com.lievasoft.bio.mapper;

import com.lievasoft.bio.controller.RegisterRequest;
import com.lievasoft.bio.entity.BioUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Mapper {

    private final PasswordEncoder passwordEncoder;

    public BioUser mapToBioUser(final RegisterRequest payload) {
        var password = passwordEncoder.encode(payload.password());
        return BioUser.builder()
                .username(payload.username())
                .password(password)
                .build();
    }
}
