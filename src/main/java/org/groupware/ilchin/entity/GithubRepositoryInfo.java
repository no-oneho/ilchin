package org.groupware.ilchin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "github_repository_info")
public class GithubRepositoryInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 45)
    @Column(name = "owner", length = 45)
    private String owner;

    @Lob
    @Column(name = "repo")
    private String repo;

    @Lob
    @Column(name = "token")
    private String token;

    public static GithubRepositoryInfo createEntity(String owner, String repo, String token) {
        return GithubRepositoryInfo.builder()
                .owner(owner)
                .repo(repo)
                .token(token)
                .build();
    }
}