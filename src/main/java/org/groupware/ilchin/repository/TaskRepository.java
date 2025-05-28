package org.groupware.ilchin.repository;

import org.groupware.ilchin.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long>, TaskCustomRepository {
}