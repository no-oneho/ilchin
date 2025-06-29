package org.groupware.ilchin.dto.project.response;

import lombok.Builder;
import org.groupware.ilchin.entity.Project;
import org.groupware.ilchin.entity.User;

import java.time.LocalDateTime;

@Builder
public record ProjectResponse(
        String name,
        String ownerName,
        Long ownerId,
        String description,
        LocalDateTime createdAt) {

    public static ProjectResponse fromEntity(Project project, User user) {
        return ProjectResponse.builder()
                .name(project.getName())
                .ownerName(user.getUsername())
                .ownerId(user.getId())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .build();
    }

}
