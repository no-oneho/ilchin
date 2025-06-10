package org.groupware.ilchin.dto.department.response;

import lombok.Builder;
import org.groupware.ilchin.entity.Department;

@Builder
public record DepartmentResp(
        Long id,
        String name,
        String description,
        Long managerId,
        String managerName,
        String tel

) {

    @Builder
    public static DepartmentResp entityToResp(Department entity) {
        return DepartmentResp.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .managerId(entity.getUser().getId())
                .managerName(entity.getUser().getUsername())
                .tel(entity.getTel())
                .build();
    }

}
