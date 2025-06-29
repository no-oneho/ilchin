package org.groupware.ilchin.dto.github_repository_info.request;

public record GithubRepositoryInfoCreate(

        String owner,
        String repo,
        String token

) {
}
