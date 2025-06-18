package org.groupware.ilchin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.dto.Response;
import org.groupware.ilchin.dto.project.request.CreateReq;
import org.groupware.ilchin.dto.project.response.ProjectResponse;
import org.groupware.ilchin.security.Auth;
import org.groupware.ilchin.service.ProjectService;
import org.groupware.ilchin.utils.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;

    @Auth
    @PostMapping
    public Response<ProjectResponse> createProject(@RequestBody @Valid CreateReq createReq) {
        return Api.success(200, "프로젝트 생성 완료", projectService.createProject(createReq));
    }

}
