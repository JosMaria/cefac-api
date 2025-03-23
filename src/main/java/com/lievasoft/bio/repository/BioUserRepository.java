package com.lievasoft.bio.repository;

import com.lievasoft.bio.entity.BioUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BioUserRepository extends JpaRepository<BioUser, Long> {

    boolean existsByUsername(String username);
}
