package org.groupware.ilchin.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.dto.user.response.UserProfileResp;
import org.groupware.ilchin.dto.user.response.UserSearchResp;
import org.groupware.ilchin.entity.User;
import org.groupware.ilchin.repository.UserCustomRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.groupware.ilchin.entity.QDepartment.department;
import static org.groupware.ilchin.entity.QUser.user;
import static org.groupware.ilchin.entity.QUserProfile.userProfile;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean isUsernameAvailable(String username) {
        return jpaQueryFactory
                .selectFrom(user)
                .where(user.username.eq(username))
                .fetchFirst() == null;
    }

    @Override
    public UserProfileResp findUserProfileByUser(User currentUser) {
        return jpaQueryFactory
                .select(Projections.constructor(UserProfileResp.class,
                        user.username,
                        user.email,
                        user.role,
                        userProfile.fullName,
                        userProfile.phone))
                .from(user)
                .join(userProfile).on(user.eq(userProfile.user))
                .where(
                        user.eq(currentUser)
                )
                .fetchFirst();
    }

    @Override
    public List<UserSearchResp> searchUserWithPage(String searchKeyword, Long departmentId, String sortType, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        if (searchKeyword != null) {
            String keywordPattern = "%" + searchKeyword + "%";

            BooleanBuilder searchBuilder = new BooleanBuilder();
            searchBuilder.and(userProfile.fullName.like(keywordPattern));
            builder.and(searchBuilder);
        }

        if (departmentId != null) {
            BooleanBuilder searchBuilder = new BooleanBuilder();
            searchBuilder.and(department.id.eq(departmentId));
            builder.and(searchBuilder);
        }


        return jpaQueryFactory
                .select(Projections.constructor(UserSearchResp.class,
                        user.id,
                        user.email,
                        user.createdAt,
                        userProfile.fullName,
                        userProfile.phone,
                        userProfile.department.id
                ))
                .from(user)
                .leftJoin(userProfile).on(user.eq(userProfile.user))
                .leftJoin(department).on(department.eq(userProfile.department))
                .where(
                        builder
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(user)
                .fetch();
    }
}
