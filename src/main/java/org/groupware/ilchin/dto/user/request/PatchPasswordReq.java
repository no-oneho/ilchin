package org.groupware.ilchin.dto.user.request;

public record PatchPasswordReq(
        String currentPassword,
        String password,
        String confirmPassword
) {
}
