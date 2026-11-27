package com.ttps.security.repository;

import com.ttps.security.entity.User;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data proporciona la implementación y las operaciones CRUD habituales.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByEmail(String email);
}
