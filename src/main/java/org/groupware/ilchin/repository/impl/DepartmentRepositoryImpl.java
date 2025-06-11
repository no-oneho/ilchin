package org.groupware.ilchin.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.entity.Department;
import org.groupware.ilchin.repository.DepartmentCustomRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.groupware.ilchin.entity.QDepartment.department;

@Repository
@RequiredArgsConstructor
public class DepartmentRepositoryImpl implements DepartmentCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Department> searchDepartmentBySearchKeywordWithId(String searchKeyword, Long departmentId, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();

        if (!(searchKeyword == null || searchKeyword.isEmpty())) {
            String escapedKeyword = searchKeyword.replace("%", "\\%").replace("_", "\\_");
            builder.and(department.name.like("%" + escapedKeyword + "%"));
        }

        if (departmentId != null) {
            builder.and(department.id.eq(departmentId));
        }

        return jpaQueryFactory
                .selectFrom(department)
                .where(
                        builder.and(department.isDeleted.eq(false))
                ).limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(department.id.desc())
                .fetch();
    }

    @Override
    public Long searchDepartmentBySearchKeywordWithIdCount(String searchKeyword, Long departmentId) {

        BooleanBuilder builder = new BooleanBuilder();

        if (!(searchKeyword == null || searchKeyword.isEmpty())) {
            String escapedKeyword = searchKeyword.replace("%", "\\%").replace("_", "\\_");
            builder.and(department.name.like("%" + escapedKeyword + "%"));
        }

        if (departmentId != null) {
            builder.and(department.id.eq(departmentId));
        }

        return jpaQueryFactory
                .select(department.id.count())
                .from(department)
                .where(
                        builder.and(department.isDeleted.eq(false))
                )
                .fetchOne();
    }
}
