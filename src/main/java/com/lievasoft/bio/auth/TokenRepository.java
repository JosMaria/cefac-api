package com.lievasoft.bio.auth;

import com.lievasoft.bio.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByValue(String value);
}
