package org.groupware.ilchin.controller;

import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.dto.Response;
import org.groupware.ilchin.dto.department.request.CreateReq;
import org.groupware.ilchin.dto.department.response.DepartmentResp;
import org.groupware.ilchin.security.Auth;
import org.groupware.ilchin.service.DepartmentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.Api;

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

}
