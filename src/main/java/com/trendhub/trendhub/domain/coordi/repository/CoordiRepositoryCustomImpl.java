package com.trendhub.trendhub.domain.coordi.repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trendhub.trendhub.domain.coordi.dto.CoordiDto;
import com.trendhub.trendhub.domain.review.entity.QReview;
import com.trendhub.trendhub.domain.user.entity.QUser;
import com.trendhub.trendhub.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<CoordiDto>coordiPage(User user, Pageable pageable){
        List<CoordiDto> content;

        //유저가 비로그인일 때
        if (user == null) {
            content = jpaQueryFactory
                    .select(Projections.constructor(CoordiDto.class,
                            coordi.coordiId,
                            coordi.user.profile,
                            coordi.user.nickname,
                            coordi.image,
                            coordi.totalLike,
                            Expressions.asBoolean(false).as("liked")
                    ))
                    .from(coordi)
                    .limit(pageable.getPageSize())
                    .offset(pageable.getOffset())
                    .fetch();
        } else {
            content = jpaQueryFactory
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
                    .leftJoin(coordi.likes)
                    .on(likes.coordi.eq(coordi).and(likes.user.eq(user)))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        }


        int total = jpaQueryFactory
                .selectFrom(coordi)
                .from(coordi)
                .fetch()
                .size();


        return new PageImpl<>(content, pageable, total);
    }


}
