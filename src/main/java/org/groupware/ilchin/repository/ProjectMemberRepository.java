package org.groupware.ilchin.repository;

import org.groupware.ilchin.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long>, ProjectMemberCustomRepository {
}