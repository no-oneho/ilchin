package org.groupware.ilchin.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.repository.UserProfileCustomRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserProfileRepositoryImpl implements UserProfileCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
}
