package org.groupware.ilchin.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.dto.Response;
import org.groupware.ilchin.dto.SearchPageResponse;
import org.groupware.ilchin.dto.github_repository_info.response.PullRequestResp;
import org.groupware.ilchin.dto.project.request.CreateReq;
import org.groupware.ilchin.dto.project.response.ProjectResponse;
import org.groupware.ilchin.entity.GithubRepositoryInfo;
import org.groupware.ilchin.entity.Project;
import org.groupware.ilchin.entity.User;
import org.groupware.ilchin.exception.*;
import org.groupware.ilchin.repository.GithubRepositoryInfoRepository;
import org.groupware.ilchin.repository.ProjectRepository;
import org.groupware.ilchin.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;
import java.util.stream.DoubleStream;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final WebClient webClient;
    private final GithubRepositoryInfoRepository githubRepositoryInfoRepository;
    private final UserUtils userUtils;
    public static final String API_URL = "https://api.github.com/";


    @Transactional
    public ProjectResponse createProject(CreateReq createReq) {
        User user = userUtils.getCurrentUser();
        if (!user.getRole().equals("ADMIN")) {
            throw new CustomException(UserException.HANDLE_ACCESS_DENIED);
        }
        GithubRepositoryInfo githubRepositoryInfo = githubRepositoryInfoRepository.findById(createReq.repositoryId())
                .orElseThrow(() -> new CustomException(GithubRepositoryInfoException.NOT_FOUND_REPOSITORY));

        Project project = Project.fromCreateDto(createReq, user, githubRepositoryInfo);
        projectRepository.save(project);

        return ProjectResponse.fromEntity(project, user);
    }

    public Mono<SearchPageResponse<Response<PullRequestResp>>> searchPullRequestByProjectId(Long projectId, Integer pageNumber, Integer pageSize, String state) {

        // todo
        return null;
    }
}
