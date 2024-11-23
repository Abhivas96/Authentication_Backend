package com.abhi.BasicAuth.repositories;

import com.abhi.BasicAuth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameOrEmail(String Username, String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
