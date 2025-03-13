package com.lievasoft.bio.repository;

import com.lievasoft.bio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
