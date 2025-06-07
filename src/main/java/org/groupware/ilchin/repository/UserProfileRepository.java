package org.groupware.ilchin.repository;

import org.groupware.ilchin.entity.User;
import org.groupware.ilchin.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long>, UserProfileCustomRepository {
    Optional<UserProfile> findByUser(User user);
}