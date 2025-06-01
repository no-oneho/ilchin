package org.groupware.ilchin.repository;

import org.groupware.ilchin.dto.user.response.UserProfileResp;
import org.groupware.ilchin.entity.User;

public interface UserCustomRepository {

    boolean isUsernameAvailable(String username);

    boolean confirmPassword(Long userId, String encode);

    UserProfileResp findUserProfileByUser(User user);
}
