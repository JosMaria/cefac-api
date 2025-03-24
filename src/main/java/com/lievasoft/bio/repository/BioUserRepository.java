package com.lievasoft.bio.repository;

import com.lievasoft.bio.entity.BioUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BioUserRepository extends JpaRepository<BioUser, Long> {

    Optional<BioUser> findByUsername(String username);
}
