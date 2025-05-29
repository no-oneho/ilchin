package org.groupware.ilchin.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.repository.DepartmentCustomRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DepartmentRepositoryImpl implements DepartmentCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
}
