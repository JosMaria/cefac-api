package com.lievasoft.bio.user;

import com.lievasoft.bio.auth.dto.RegisterRequest;
import com.lievasoft.bio.entity.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserMapper {

    private final PasswordEncoder passwordEncoder;

    public CustomUser map(final RegisterRequest payload) {
        var password = passwordEncoder.encode(payload.password());
        return CustomUser.builder()
                .username(payload.username())
                .password(password)
                .build();
    }
}
