package org.groupware.ilchin.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.dto.department.request.CreateReq;
import org.groupware.ilchin.dto.department.response.DepartmentResp;
import org.groupware.ilchin.entity.Department;
import org.groupware.ilchin.entity.User;
import org.groupware.ilchin.exception.CustomException;
import org.groupware.ilchin.exception.UserException;
import org.groupware.ilchin.repository.DepartmentRepository;
import org.groupware.ilchin.repository.UserProfileRepository;
import org.groupware.ilchin.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    @Transactional
    public DepartmentResp createDepartment(CreateReq createReq) {

        User user = userRepository.findById(createReq.managerUserId())
                .orElseThrow(() -> new CustomException(UserException.NOT_FOUND_USER));

        Department department = Department.create(createReq, user);
        return DepartmentResp.entityToResp(departmentRepository.save(department));

    }
}
