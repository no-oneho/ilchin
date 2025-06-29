package org.groupware.ilchin.dto.github_repository_info.response;

import java.time.LocalDateTime;

public record PullRequestResp(
        long id,
        int number,
        String state,
        String title,
        String body,
        String htmlUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime closedAt,
        LocalDateTime mergedAt,
        GithubUser user,
        String baseRef,
        String headRef,
        int comments,
        int reviewComments,
        int commits,
        int additions,
        int deletions,
        int changedFiles
) {
    public record GithubUser(
            long id,
            String login,
            String avatarUrl,
            String htmlUrl
    ) {}
}