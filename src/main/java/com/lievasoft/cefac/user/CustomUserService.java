package com.lievasoft.cefac.user;

import com.lievasoft.cefac.user.dto.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface CustomUserService extends UserDetailsService {

    List<UserResponseDto> obtainAllUsers();
}
