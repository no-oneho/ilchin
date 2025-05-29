package org.groupware.ilchin.service;

import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.dto.user.request.SignUp;
import org.groupware.ilchin.entity.User;
import org.groupware.ilchin.entity.UserProfile;
import org.groupware.ilchin.exception.CustomException;
import org.groupware.ilchin.exception.UserException;
import org.groupware.ilchin.repository.UserProfileRepository;
import org.groupware.ilchin.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.ref.PhantomReference;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Void createUser(SignUp signUp) {
        checkUserExist(signUp.getUsername());
        checkPasswordConfirm(signUp.getPassword(), signUp.getConfirmPassword());

        User user = userRepository.save(User.SignUpToUser(signUp, passwordEncoder.encode(signUp.getPassword())));
        userProfileRepository.save(UserProfile.SignUpToUserProfile(user.getId(), signUp));
        return null;
    }

    private void checkUserExist(String username) {
        if (!userRepository.isUsernameAvailable(username)) {
            throw new CustomException(UserException.ALREADY_EXISTS);
        }
    }

    private void checkPasswordConfirm(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new CustomException(UserException.MISS_MATCH_PASSWORD);
        }
    }
}
