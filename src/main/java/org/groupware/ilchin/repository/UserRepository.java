package org.groupware.ilchin.repository;

import org.groupware.ilchin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {
    Optional<User> findByUsername(String username);
}