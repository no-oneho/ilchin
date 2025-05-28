package org.groupware.ilchin.repository;

import org.groupware.ilchin.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long>, DepartmentCustomRepository {
}