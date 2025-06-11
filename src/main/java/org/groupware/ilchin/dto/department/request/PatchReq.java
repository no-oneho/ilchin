package org.groupware.ilchin.dto.department.request;

import jakarta.validation.constraints.NotBlank;

public record PatchReq(
        @NotBlank(message = "부서명은 빈값일 수 없습니다.") String name,
        @NotBlank(message = "부서 설명은 빈값일 수 없습니다.") String description
) {
}
