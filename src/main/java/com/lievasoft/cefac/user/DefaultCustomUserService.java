package com.lievasoft.cefac.user;

import com.lievasoft.cefac.entity.CustomUser;
import com.lievasoft.cefac.entity.Role;
import com.lievasoft.cefac.exception.types.InvalidOperationException;
import com.lievasoft.cefac.user.dto.UserResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.lievasoft.cefac.entity.Role.ADMIN;

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

    @Override
    public List<UserResponseDto> obtainAllUsers() {
        return customUserRepository.findUsersList();
    }

    @Transactional
    @Override
    public void toggleEnabledState(UUID uuid) {
        CustomUser obtainedUser = customUserRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("User with id: '%s' hasn't been founded".formatted(uuid)));

        if (!obtainedUser.getRole().equals(ADMIN)) {
            obtainedUser.setEnabled(!obtainedUser.isEnabled());
        } else {
            throw new InvalidOperationException();
        }
    }
}
