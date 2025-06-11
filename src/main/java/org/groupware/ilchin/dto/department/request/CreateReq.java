package org.groupware.ilchin.dto.department.request;

import jakarta.validation.constraints.NotBlank;

public record CreateReq(
        @NotBlank(message = "부서명은 빈 값일 수 없습니다.") String name,
        @NotBlank(message = "부서설명은 빈 값일 수 없습니다.") String description,
        @NotBlank(message = "부서장을 선택해주세요.") Long managerUserId,
        String tel
) {
}
