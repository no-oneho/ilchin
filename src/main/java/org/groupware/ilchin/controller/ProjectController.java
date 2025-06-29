package org.groupware.ilchin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.dto.Response;
import org.groupware.ilchin.dto.SearchPageResponse;
import org.groupware.ilchin.dto.github_repository_info.response.PullRequestResp;
import org.groupware.ilchin.dto.project.request.CreateReq;
import org.groupware.ilchin.dto.project.response.ProjectResponse;
import org.groupware.ilchin.security.Auth;
import org.groupware.ilchin.service.ProjectService;
import org.groupware.ilchin.utils.Api;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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

    @GetMapping("{projectId}")
    public Mono<SearchPageResponse<Response<PullRequestResp>>> searchPullRequestsByProjectId(
            @PathVariable("projectId") Long projectId,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) String state
    ) {

        return projectService.searchPullRequestByProjectId(
                projectId,
                pageNumber,
                pageSize,
                state
        ).map(result -> Api.success(200, "PR 리스트 조회 성공", result).getData());

    }

}
