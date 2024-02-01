package com.trendhub.trendhub.domain.coordi.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trendhub.trendhub.domain.coordi.dto.CoordiDto;
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
        JPAQuery<CoordiDto> jpaQuery = jpaQueryFactory
                .select(Projections.constructor(CoordiDto.class,
                        coordi.coordiId,
                        coordi.image,
                        coordi.totalLike,
                        likes.likesId,
                        new CaseBuilder()
                                .when(likes.likesId.isNotNull()).then(true)
                                .otherwise(false).as("liked")
                ))
                .from(coordi)
                .leftJoin(likes)
                .on(likes.coordi.eq(coordi));

        if (user != null) {
            jpaQuery = jpaQuery.on(likes.user.eq(user));
        }

        List<CoordiDto> result = jpaQuery
                .orderBy(coordi.viewCount.desc())
                .limit(5)
                .fetch();

        return result;
    }

}
