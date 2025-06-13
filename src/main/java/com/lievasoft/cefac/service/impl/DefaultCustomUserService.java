package com.lievasoft.cefac.service.impl;

import com.lievasoft.cefac.dto.user.UserResponseDto;
import com.lievasoft.cefac.entity.user.CustomUser;
import com.lievasoft.cefac.exception.types.OperationNotAllowedException;
import com.lievasoft.cefac.service.CustomUserService;
import com.lievasoft.cefac.repository.CustomUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.lievasoft.cefac.entity.user.Role.ADMIN;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultCustomUserService implements CustomUserService {

    private final CustomUserRepository customUserRepository;

    @Override
    public CustomUser loadUserByUsername(final String username) throws UsernameNotFoundException {
        return customUserRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public List<UserResponseDto> obtainAllUsers() {
        return customUserRepository.findUsersList();
    }

    @Transactional
    @Override
    public void toggleDisabledState(final UUID uuid) {
        CustomUser obtainedUser = customUserRepository.findByUuid(uuid)
                .filter(userToUpdate -> !userToUpdate.getRole().equals(ADMIN))
                .orElseThrow(OperationNotAllowedException::new);

        obtainedUser.setDisabled(!obtainedUser.isDisabled());
    }
}
