package org.groupware.ilchin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pull_request")
public class PullRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "creator_login", nullable = false)
    private String creatorLogin;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}