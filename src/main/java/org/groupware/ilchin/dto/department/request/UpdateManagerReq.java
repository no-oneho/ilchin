package org.groupware.ilchin.dto.department.request;

import jakarta.validation.constraints.NotNull;

public record UpdateManagerReq(
        @NotNull(message = "담당 매니저를 선택 해 주세요") Long managerId
) {
}
