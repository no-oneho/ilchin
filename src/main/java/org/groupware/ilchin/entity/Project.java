package org.groupware.ilchin.entity;

import jakarta.persistence.*;
import lombok.*;
import org.groupware.ilchin.dto.project.request.CreateReq;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "owner_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @JoinColumn(name = "github_repository_id", nullable = false)
    @ManyToOne
    private GithubRepositoryInfo githubRepositoryInfo;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "state", nullable = false, length = 50)
    private String state;

    public static Project fromCreateDto(CreateReq createReq, User user, GithubRepositoryInfo githubRepositoryInfo) {
        return Project.builder()
                .owner(user)
                .name(createReq.name())
                .description(createReq.description())
                .githubRepositoryInfo(githubRepositoryInfo)
                .createdAt(LocalDateTime.now())
                .state("OPEN")
                .build();
    }

}