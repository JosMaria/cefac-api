package com.lievasoft.bio.auth;

import com.lievasoft.bio.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {


}
