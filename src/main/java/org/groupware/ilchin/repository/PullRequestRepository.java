package org.groupware.ilchin.repository;

import org.groupware.ilchin.entity.PullRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PullRequestRepository extends JpaRepository<PullRequest, Long>, PullRequestCustomRepository {
}