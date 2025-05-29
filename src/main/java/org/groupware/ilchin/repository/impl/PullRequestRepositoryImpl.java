package org.groupware.ilchin.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.repository.PullRequestCustomRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PullRequestRepositoryImpl implements PullRequestCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

}
