package org.groupware.ilchin.controller;

import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.dto.Response;
import org.groupware.ilchin.dto.SearchPageResponse;
import org.groupware.ilchin.dto.department.request.CreateReq;
import org.groupware.ilchin.dto.department.response.DepartmentResp;
import org.groupware.ilchin.security.Auth;
import org.groupware.ilchin.service.DepartmentService;
import org.springframework.web.bind.annotation.*;
import org.groupware.ilchin.utils.Api;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Auth
    @PostMapping
    public Response<DepartmentResp> createDepartment(@RequestBody CreateReq createReq) {
        return Api.success(200, "부서 생성 완료", departmentService.createDepartment(createReq));
    }

    @Auth
    @GetMapping
    public Response<SearchPageResponse<DepartmentResp>> searchDepartment(
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(required = false) Long departmentId,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize
    ) {
        return Api.success(200, "부서 리스트 조회 완료", departmentService.searchDepartment(searchKeyword, departmentId, pageNumber, pageSize));
    }

    @GetMapping({"{id}"})
    public Response<DepartmentResp> getDepartment(@PathVariable Long id) {
        return Api.success(200, "부서 조회 완료", departmentService.getDepartment(id));
    }

}
