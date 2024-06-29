package com.gt.genti.user.repository;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;

import com.gt.genti.user.model.User;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    @Cacheable(value = "user", cacheManager = "redisCacheManager")
    public Optional<User> findByIdWithCache(final Long id) {
        // return Optional.ofNullable(queryFactory.selectFrom(user)
        //         .where(user.id.eq(id)).fetchOne());
        return null;
    }

}
