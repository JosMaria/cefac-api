package com.lievasoft.cefac.auth;

import com.lievasoft.cefac.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {

    Optional<Token> findByToken(String value);

    @Transactional
    @Modifying
    @Query("""
        UPDATE Token
        SET revoked = TRUE
        WHERE user.id = :userId
    """)
    int revokeTokensByUserId(@Param("userId") Long userId);
}
