package org.groupware.ilchin.repository;

import org.groupware.ilchin.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectCustomRepository {
}