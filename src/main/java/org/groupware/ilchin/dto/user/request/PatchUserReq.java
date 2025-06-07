package org.groupware.ilchin.dto.user.request;

public record PatchUserReq(
        String email,
        String fullName,
        String phoneNumber
) {
}
