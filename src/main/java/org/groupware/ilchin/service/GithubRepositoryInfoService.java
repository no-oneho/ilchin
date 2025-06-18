package org.groupware.ilchin.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.dto.github_repository_info.request.GithubRepositoryInfoCreate;
import org.groupware.ilchin.entity.GithubRepositoryInfo;
import org.groupware.ilchin.exception.CustomException;
import org.groupware.ilchin.exception.ExtendApiException;
import org.groupware.ilchin.repository.GithubRepositoryInfoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class GithubRepositoryInfoService {

    public static final String API_URL = "https://api.github.com/";
    private final GithubRepositoryInfoRepository repositoryInfoRepository;
    private final WebClient webClient;


    @Transactional
    public Mono<GithubRepositoryInfoCreate> createGitHubRepositoryInfo(GithubRepositoryInfoCreate request) {
        return webClient.get()
                .uri(API_URL + "/repos/{owner}/{repo}", request.owner(), request.repo())
                .headers(header -> header.setBearerAuth(request.token()))
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    if (response == null || response.isEmpty()) {
                        return Mono.error(new CustomException(ExtendApiException.BAD_REQUEST_INFO));
                    }
                    GithubRepositoryInfo githubRepositoryInfo = GithubRepositoryInfo.createEntity(request.owner(), request.repo(), request.token());
                    return Mono.fromCallable(() -> repositoryInfoRepository.save(githubRepositoryInfo))
                            .subscribeOn(Schedulers.boundedElastic())
                            .map(savedInfo -> new GithubRepositoryInfoCreate(savedInfo.getOwner(), savedInfo.getRepo(), savedInfo.getToken()));
                })
                .onErrorMap((e) -> {
                    throw new CustomException(ExtendApiException.BAD_REQUEST_INFO);
                });

    }

}
