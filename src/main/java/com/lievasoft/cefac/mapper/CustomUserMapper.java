package com.lievasoft.cefac.mapper;

import com.lievasoft.cefac.dto.auth.RegisterRequestDto;
import com.lievasoft.cefac.entity.user.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserMapper {

    private final PasswordEncoder passwordEncoder;

    public CustomUser mapToCustomUser(final RegisterRequestDto payload) {
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
