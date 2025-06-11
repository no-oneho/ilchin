package org.groupware.ilchin.utils;

import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.entity.User;
import org.groupware.ilchin.exception.CustomException;
import org.groupware.ilchin.exception.UserException;
import org.groupware.ilchin.repository.UserRepository;
import org.groupware.ilchin.security.AuthHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtils {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        Long userId = AuthHolder.getUserId();
        if (userId == null) {
            throw new CustomException(UserException.HANDLE_ACCESS_DENIED);
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserException.HANDLE_ACCESS_DENIED));
    }

}
