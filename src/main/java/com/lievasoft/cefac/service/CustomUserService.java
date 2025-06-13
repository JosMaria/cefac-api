package com.lievasoft.cefac.service;

import com.lievasoft.cefac.dto.user.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface CustomUserService extends UserDetailsService {

    List<UserResponseDto> obtainAllUsers();

    void toggleDisabledState(UUID uuid);
}
