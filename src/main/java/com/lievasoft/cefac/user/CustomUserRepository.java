package com.lievasoft.cefac.user;

import com.lievasoft.cefac.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {

    Optional<CustomUser> findByUsername(String username);

    boolean existsByUsername(String username);
}
