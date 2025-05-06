package com.lievasoft.cefac.user;

import com.lievasoft.cefac.entity.CustomUser;
import com.lievasoft.cefac.user.dto.UserResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {

    Optional<CustomUser> findByUsername(String username);

    boolean existsByEmail(String email);

    @Query(name = "CustomUser.findUserList", nativeQuery = true)
    List<UserResponseDto> findUsersList();
}
