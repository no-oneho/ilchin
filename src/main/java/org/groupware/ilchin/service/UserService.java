package org.groupware.ilchin.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.dto.SearchPageResponse;
import org.groupware.ilchin.dto.user.request.LoginReq;
import org.groupware.ilchin.dto.user.request.PatchPasswordReq;
import org.groupware.ilchin.dto.user.request.PatchUserReq;
import org.groupware.ilchin.dto.user.request.SignUp;
import org.groupware.ilchin.dto.user.response.LoginResp;
import org.groupware.ilchin.dto.user.response.UserProfileResp;
import org.groupware.ilchin.dto.user.response.UserSearchResp;
import org.groupware.ilchin.entity.Department;
import org.groupware.ilchin.entity.User;
import org.groupware.ilchin.entity.UserProfile;
import org.groupware.ilchin.exception.CustomException;
import org.groupware.ilchin.exception.DepartmentException;
import org.groupware.ilchin.exception.UserException;
import org.groupware.ilchin.repository.DepartmentRepository;
import org.groupware.ilchin.repository.UserProfileRepository;
import org.groupware.ilchin.repository.UserRepository;
import org.groupware.ilchin.security.TokenProvider;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.groupware.ilchin.utils.Api;
import org.groupware.ilchin.utils.UserUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserUtils userUtils;

    @Transactional
    public LoginResp createUser(SignUp signUp) {
        checkUserExist(signUp.getUsername());
        checkPasswordConfirm(signUp.getPassword(), signUp.getConfirmPassword());

        User user = userRepository.save(User.SignUpToUser(signUp, passwordEncoder.encode(signUp.getPassword())));
        Department department = departmentRepository.findById(signUp.getDepartment())
                .orElseThrow(() -> new CustomException(DepartmentException.NOT_FOUND_DEPARTMENT));
        userProfileRepository.save(UserProfile.SignUpToUserProfile(user, signUp, department));
        String token = TokenProvider.createToken(user);
        return LoginResp.from(user, token);
    }

    @Transactional
    public LoginResp login(LoginReq login) {
        User user = userRepository.findByUsername(login.getUsername())
                .orElseThrow(() -> new CustomException(UserException.BAD_REQUEST_LOGIN));

        if (user.getIsDeleted()) {
            throw new CustomException(UserException.BAD_REQUEST_LOGIN);
        }

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new CustomException(UserException.BAD_REQUEST_LOGIN);
        }

        String token = TokenProvider.createToken(user);
        return LoginResp.from(user, token);
    }


    public UserProfileResp getCurrentUserProfile() {
        User user = userUtils.getCurrentUser();
        return userRepository.findUserProfileByUser(user);
    }

    @Transactional
    public UserProfileResp patchCurrentUserProfile(PatchUserReq patchUserReq) {
        if (!Api.areFieldsNotNullOrEmpty(patchUserReq, "email", "fullName", "phoneNumber")) {
            throw new CustomException(UserException.BAD_REQUEST_PATCH);
        }
        User user = userUtils.getCurrentUser();
        UserProfile userProfile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(UserException.NOT_FOUND_USER));
        user.updateEmail(patchUserReq.email());
        userProfile.update(patchUserReq.fullName(), patchUserReq.phoneNumber());
        userProfileRepository.save(userProfile);
        userRepository.save(user);

        return new UserProfileResp(user.getUsername(), user.getEmail(), user.getRole(), userProfile.getFullName(), userProfile.getPhone());

    }

    public UserProfileResp getTargetUserProfile(Long id) {
        User currentUser = userUtils.getCurrentUser();
        UserProfile currentUserProfile = userProfileRepository.findByUser(currentUser)
                .orElseThrow(() -> new CustomException(UserException.NOT_FOUND_USER));
        User targetUser = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(UserException.NOT_FOUND_USER));
        UserProfile targetUserProfile = userProfileRepository.findByUser(targetUser)
                .orElseThrow(() -> new CustomException(UserException.NOT_FOUND_USER));

        checkUserAuthentication(currentUser, currentUserProfile, targetUserProfile);
        return userRepository.findUserProfileByUser(targetUser);
    }

    @Transactional
    public String patchCurrentUserPassword(PatchPasswordReq patchPasswordReq) {
        User user = userUtils.getCurrentUser();
        if (!passwordEncoder.matches(patchPasswordReq.currentPassword(), user.getPassword())) {
            throw new CustomException(UserException.BAD_REQUEST_LOGIN);
        }
        checkPasswordConfirm(patchPasswordReq.password(), patchPasswordReq.confirmPassword());
        user.changePassword(passwordEncoder.encode(patchPasswordReq.password()));
        return "패스워드가 변경되었으니 재 로그인 해주세요";
    }

    @Transactional
    public String deleteTargetUser(Long id) {
        User currentUser = userUtils.getCurrentUser();
        UserProfile currentUserProfile = userProfileRepository.findByUser(currentUser)
                .orElseThrow(() -> new CustomException(UserException.NOT_FOUND_USER));
        User targetUser = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(UserException.NOT_FOUND_USER));
        UserProfile targetUserProfile = userProfileRepository.findByUser(targetUser)
                .orElseThrow(() -> new CustomException(UserException.NOT_FOUND_USER));

        checkUserAuthentication(currentUser, currentUserProfile, targetUserProfile);

        targetUser.deleteUser();
        userRepository.save(targetUser);
        return "사용자 제거 완료";

    }


    public SearchPageResponse<UserSearchResp> searchUser(String searchKeyword, Long departmentId,
                                                         Integer pageNumber, Integer pageSize,
                                                         String sortType) {
        User user = userUtils.getCurrentUser();
        Pageable pageable = PageRequest.ofSize(pageSize).withPage(pageNumber);
        List<UserSearchResp> userSearchRespList = userRepository.searchUserWithPage(
                searchKeyword, departmentId, user, sortType, pageable
        );

        Long totalCount = userRepository.searchUserCount(searchKeyword, departmentId, user);
        Long totalPage = (long)Math.ceil((double)totalCount / pageSize);


        return SearchPageResponse.of(totalCount, totalPage, pageNumber + 1, pageSize, userSearchRespList);
    }

    private static void checkUserAuthentication(User currentUser, UserProfile currentUserProfile, UserProfile targetUserProfile) {
        boolean isAdmin = currentUser.getRole().equals("ADMIN");

        if (!isAdmin) {
            // 관리자가 아니면, 부서 매니저인지 확인
            Long currentDeptId = currentUserProfile.getDepartment().getId();
            Long targetDeptId = targetUserProfile.getDepartment().getId();
            Long currentUserId = currentUser.getId();
            Long targetUserDeptManagerId = currentUserProfile.getDepartment().getUser().getId();

            // 같은 부서이고, 현재 유저가 그 부서 관리자이면 통과
            if (!(currentDeptId.equals(targetDeptId) && currentUserId.equals(targetUserDeptManagerId))) {
                throw new CustomException(UserException.FORBIDDEN_ACCESS);
            }
        }
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
