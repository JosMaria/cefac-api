package com.lievasoft.bio.service.impl;

import com.lievasoft.bio.entity.CustomUser;
import com.lievasoft.bio.repository.CustomUserRepository;
import com.lievasoft.bio.service.CustomUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultCustomUserService implements CustomUserService {

    private final CustomUserRepository customUserRepository;

    @Override
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return customUserRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
