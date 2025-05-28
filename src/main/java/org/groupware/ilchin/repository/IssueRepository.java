package org.groupware.ilchin.repository;

import org.groupware.ilchin.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long>, IssueCustomRepository {
}