package com.lievasoft.cefac.user;

import com.lievasoft.cefac.auth.dto.RegisterRequest;
import com.lievasoft.cefac.entity.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserMapper {

    private final PasswordEncoder passwordEncoder;

    public CustomUser map(final RegisterRequest payload) {
        var passwordTemp = passwordEncoder.encode(payload.email());
        return CustomUser.builder()
                .name(payload.name())
                .lastname(payload.lastname())
                .email(payload.email())
                .username(payload.email())
                .password(passwordTemp)
                .role(payload.role())
                .build();
    }
}
