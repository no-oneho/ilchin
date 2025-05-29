package org.groupware.ilchin.repository;

import org.groupware.ilchin.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long>, UserProfileCustomRepository {
}