package com.trendhub.trendhub.domain.product.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trendhub.trendhub.domain.product.dto.ProductDto;
import com.trendhub.trendhub.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.trendhub.trendhub.domain.likes.entity.QLikes.*;
import static com.trendhub.trendhub.domain.product.entity.QProduct.*;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ProductDto> findTop10ByOrderByViewCountDesc(User user) {
        JPAQuery<ProductDto> jpaQuery = jpaQueryFactory
                .select(Projections.constructor(ProductDto.class,
                        product.productId,
                        product.image,
                        product.name,
                        product.price,
                        product.totalLike,
                        likes.likesId,
                        new CaseBuilder()
                                .when(likes.likesId.isNotNull()).then(true)
                                .otherwise(false).as("liked")
                ))
                .from(product)
                .leftJoin(likes)
                .on(likes.product.eq(product));

        if (user != null) {
            jpaQuery = jpaQuery.on(likes.user.eq(user));
        }

        List<ProductDto> result = jpaQuery
                .orderBy(product.viewCount.desc())
                .limit(10)
                .fetch();
        return result;
    }
}
