package org.groupware.ilchin.dto.github_repository_info.response;

public record GithubRepositoryInfoResp(
        String owner,
        String repo,
        String url
) {
}
