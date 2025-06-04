package org.groupware.ilchin.dto.user.request;

public record PatchPasswordReq(
        String password,
        String confirmPassword
) {
}
