package com.lievasoft.cefac.user;

import com.lievasoft.cefac.user.dto.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface CustomUserService extends UserDetailsService {

    List<UserResponseDto> obtainAllUsers();

    void toggleDisabledState(UUID uuid);

    void modifyUsername(String username);
}
