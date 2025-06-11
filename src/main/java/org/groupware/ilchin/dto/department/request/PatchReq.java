package org.groupware.ilchin.dto.department.request;

import jakarta.validation.constraints.NotBlank;

public record PatchReq(
        String name,
        String description
) {
}
