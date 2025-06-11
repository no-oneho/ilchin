package org.groupware.ilchin.repository;

import org.groupware.ilchin.dto.department.response.DepartmentResp;
import org.groupware.ilchin.entity.Department;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentCustomRepository {
    List<Department> searchDepartmentBySearchKeywordWithId(String searchKeyword, Long departmentId, Pageable pageable);

    Long searchDepartmentBySearchKeywordWithIdCount(String searchKeyword, Long departmentId);
}
