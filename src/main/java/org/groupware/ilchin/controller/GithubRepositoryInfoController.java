package org.groupware.ilchin.controller;


import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.dto.Response;
import org.groupware.ilchin.dto.github_repository_info.request.GithubRepositoryInfoCreate;
import org.groupware.ilchin.entity.GithubRepositoryInfo;
import org.groupware.ilchin.security.Auth;
import org.groupware.ilchin.service.GithubRepositoryInfoService;
import org.groupware.ilchin.utils.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/github")
@RequiredArgsConstructor
public class GithubRepositoryInfoController {

    private final GithubRepositoryInfoService githubRepositoryInfoService;

    @Auth
    @PostMapping
    public Mono<Response<GithubRepositoryInfoCreate>> createGithubRepositoryInfo(@RequestBody GithubRepositoryInfoCreate request) {
        return githubRepositoryInfoService.createGitHubRepositoryInfo(request)
                .map(result -> Api.success(200, "깃헙 정보 생성 완료", result));
    }

}
