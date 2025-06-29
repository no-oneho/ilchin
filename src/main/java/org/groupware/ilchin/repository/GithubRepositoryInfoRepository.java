package org.groupware.ilchin.repository;

import org.groupware.ilchin.entity.GithubRepositoryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GithubRepositoryInfoRepository extends JpaRepository<GithubRepositoryInfo, Long> {
}
