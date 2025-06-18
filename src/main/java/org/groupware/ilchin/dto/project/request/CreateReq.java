package org.groupware.ilchin.dto.project.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateReq(
        @NotNull Long ownerId,
        @NotBlank String name,
        @NotBlank String description,
        @NotBlank Long repositoryId
) {
}
