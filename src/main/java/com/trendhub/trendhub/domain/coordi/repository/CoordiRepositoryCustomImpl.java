package com.trendhub.trendhub.domain.coordi.repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trendhub.trendhub.domain.coordi.dto.CoordiDto;
import com.trendhub.trendhub.domain.review.entity.QReview;
import com.trendhub.trendhub.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.trendhub.trendhub.domain.coordi.entity.QCoordi.*;
import static com.trendhub.trendhub.domain.likes.entity.QLikes.*;


@Repository
@RequiredArgsConstructor
public class CoordiRepositoryCustomImpl implements CoordiRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CoordiDto> findTop5ByOrderByViewCountDesc(User user) {
        List<CoordiDto> result = jpaQueryFactory
                .select(Projections.constructor(CoordiDto.class,
                        coordi.coordiId,
                        coordi.user.profile,
                        coordi.user.nickname,
                        coordi.image,
                        coordi.totalLike,
                        new CaseBuilder()
                                .when(likes.likesId.isNotNull()).then(true)
                                .otherwise(false).as("liked")
                ))
                .from(coordi)
                .leftJoin(likes)
                .on(likes.coordi.eq(coordi).and(likes.user.eq(user)))
                .limit(5)
                .fetch();

        return result;
    }

    @Override
    public List<CoordiDto> findTop5ByOrderByViewCountDescAnonymousUser() {
        List<CoordiDto> result = jpaQueryFactory
                .select(Projections.constructor(CoordiDto.class,
                        coordi.coordiId,
                        coordi.user.profile,
                        coordi.user.nickname,
                        coordi.image,
                        coordi.totalLike,
                        Expressions.asBoolean(false).as("liked")
                ))
                .from(coordi)
                .orderBy(coordi.viewCount.desc())
                .limit(5)
                .fetch();

        return result;
    }

}
