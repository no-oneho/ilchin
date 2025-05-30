package org.groupware.ilchin.repository;

import org.groupware.ilchin.entity.User;

import java.util.Optional;

public interface UserCustomRepository {

    boolean isUsernameAvailable(String username);

    boolean confirmPassword(Long userId, String encode);
}
