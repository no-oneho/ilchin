package org.groupware.ilchin.dto.user.response;

import java.time.LocalDateTime;

public record UserSearchResp (
        Long userId,
        String userEmail,
        LocalDateTime createAt,
        String fullName,
        String phoneNumber,
        Long departmentId
) {
}
