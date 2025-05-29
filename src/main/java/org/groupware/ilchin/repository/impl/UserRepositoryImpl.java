package org.groupware.ilchin.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.entity.QUser;
import org.groupware.ilchin.repository.UserCustomRepository;
import org.springframework.stereotype.Repository;

import static org.groupware.ilchin.entity.QUser.user;

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
}
