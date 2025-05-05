package com.lievasoft.cefac.user;

import com.lievasoft.cefac.entity.CustomUser;
import com.lievasoft.cefac.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDefaultService implements CustomUserService {

    private final CustomUserRepository customUserRepository;

    @Override
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return customUserRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public List<UserResponseDto> obtainAllUsers() {
        return Collections.emptyList();
    }
}
