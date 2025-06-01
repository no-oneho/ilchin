package org.groupware.ilchin.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.dto.user.request.LoginReq;
import org.groupware.ilchin.dto.user.request.SignUp;
import org.groupware.ilchin.dto.user.response.LoginResp;
import org.groupware.ilchin.entity.User;
import org.groupware.ilchin.entity.UserProfile;
import org.groupware.ilchin.exception.CustomException;
import org.groupware.ilchin.exception.UserException;
import org.groupware.ilchin.repository.UserProfileRepository;
import org.groupware.ilchin.repository.UserRepository;
import org.groupware.ilchin.security.TokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public LoginResp createUser(SignUp signUp) {
        checkUserExist(signUp.getUsername());
        checkPasswordConfirm(signUp.getPassword(), signUp.getConfirmPassword());

        User user = userRepository.save(User.SignUpToUser(signUp, passwordEncoder.encode(signUp.getPassword())));
        userProfileRepository.save(UserProfile.SignUpToUserProfile(user.getId(), signUp));
        String token = TokenProvider.createToken(user);
        return LoginResp.from(user, token);
    }

    @Transactional
    public LoginResp login(LoginReq login) {
        User user = userRepository.findByUsername(login.getUsername())
                .orElseThrow(()->new CustomException(UserException.BAD_REQUEST_LOGIN));

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new CustomException(UserException.BAD_REQUEST_LOGIN);
        }

        String token = TokenProvider.createToken(user);
        return LoginResp.from(user, token);
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
