package org.groupware.ilchin.repository;

import org.groupware.ilchin.dto.user.response.UserProfileResp;
import org.groupware.ilchin.dto.user.response.UserSearchResp;
import org.groupware.ilchin.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserCustomRepository {

    boolean isUsernameAvailable(String username);

    boolean confirmPassword(Long userId, String encode);

    UserProfileResp findUserProfileByUser(User user);

    List<UserSearchResp> searchUserWithPage(String searchKeyword, Long departmentId, String sortType, Pageable pageable);
}
