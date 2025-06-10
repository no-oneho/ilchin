package org.groupware.ilchin.dto.department.request;

public record CreateReq(
        String name,
        String description,
        Long managerUserId,
        String tel
) {
}
