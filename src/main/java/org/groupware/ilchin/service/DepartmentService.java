package org.groupware.ilchin.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.dto.SearchPageResponse;
import org.groupware.ilchin.dto.department.request.CreateReq;
import org.groupware.ilchin.dto.department.request.PatchReq;
import org.groupware.ilchin.dto.department.request.UpdateManagerReq;
import org.groupware.ilchin.dto.department.response.DepartmentResp;
import org.groupware.ilchin.entity.Department;
import org.groupware.ilchin.entity.User;
import org.groupware.ilchin.exception.CustomException;
import org.groupware.ilchin.exception.DepartmentException;
import org.groupware.ilchin.exception.UserException;
import org.groupware.ilchin.repository.DepartmentRepository;
import org.groupware.ilchin.repository.UserProfileRepository;
import org.groupware.ilchin.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.groupware.ilchin.utils.UserUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserUtils userUtils;

    @Transactional
    public DepartmentResp createDepartment(CreateReq createReq) {

        User user = userRepository.findById(createReq.managerUserId())
                .orElseThrow(() -> new CustomException(UserException.NOT_FOUND_USER));

        Department department = Department.create(createReq, user);
        return DepartmentResp.entityToResp(departmentRepository.save(department));

    }

    public SearchPageResponse<DepartmentResp> searchDepartment(String searchKeyword, Long departmentId, Integer pageNumber, Integer pageSize) {
        User user = userUtils.getCurrentUser();
        if (!user.getRole().equals("ADMIN")) {
            throw new CustomException(UserException.FORBIDDEN_ACCESS);
        }

        Pageable pageable = PageRequest.ofSize(pageSize).withPage(pageNumber);
        List<DepartmentResp> departmentRespList = departmentRepository.searchDepartmentBySearchKeywordWithId(
                        searchKeyword, departmentId, pageable)
                .stream().map(DepartmentResp::entityToResp).toList();

        Long totalCount = departmentRepository.searchDepartmentBySearchKeywordWithIdCount(searchKeyword, departmentId);
        Long totalPage = (long) Math.ceil((double) totalCount / pageSize);

        return SearchPageResponse.of(totalCount, totalPage, pageNumber + 1, pageSize, departmentRespList);

    }

    public DepartmentResp getDepartment(Long id) {
        return DepartmentResp.entityToResp(departmentRepository.findById(id)
                .orElseThrow(() -> new CustomException(DepartmentException.NOT_FOUND_DEPARTMENT)));
    }

    @Transactional
    public DepartmentResp updateDepartment(Long id, PatchReq patchReq) {
        User currentUser = userUtils.getCurrentUser();
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new CustomException(DepartmentException.NOT_FOUND_DEPARTMENT));
        if (!currentUser.getRole().equals("ADMIN")) {
            if (!department.getUser().getId().equals(currentUser.getId())) {
                throw new CustomException(UserException.FORBIDDEN_ACCESS);
            }
        }
        department.updateDepartment(patchReq);

        return DepartmentResp.entityToResp(departmentRepository.save(department));
    }

    @Transactional
    public DepartmentResp updateDepartmentManager(Long id, UpdateManagerReq updateManagerReq) {
        User currentUser = userUtils.getCurrentUser();
        if (!currentUser.getRole().equals("ADMIN")) {
            throw new CustomException(UserException.FORBIDDEN_ACCESS);
        }
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new CustomException(DepartmentException.NOT_FOUND_DEPARTMENT));
        User manager = userRepository.findById(updateManagerReq.managerId())
                        .orElseThrow(() -> new CustomException(UserException.NOT_FOUND_USER));


        department.updateManager(manager);
        return DepartmentResp.entityToResp(departmentRepository.save(department));
    }

    @Transactional
    public String deleteDepartment(Long id) {
        User currentUser = userUtils.getCurrentUser();
        if (!currentUser.getRole().equals("ADMIN")) {
            throw new CustomException(UserException.FORBIDDEN_ACCESS);
        }
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new CustomException(DepartmentException.NOT_FOUND_DEPARTMENT));
        department.deleteDepartment();
        departmentRepository.save(department);
        return "부서 제거 완료";
    }
}
