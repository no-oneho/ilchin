package org.groupware.ilchin.dto.user.response;

import lombok.Builder;

@Builder
public record UserProfileResp(
        String username,
        String email,
        String role,
        String fullName,
        String phoneNumber
) {}
