package com.trendhub.trendhub.domain.review.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trendhub.trendhub.domain.orderDetail.entity.QOrderDetail;
import com.trendhub.trendhub.domain.orders.entity.QOrders;
import com.trendhub.trendhub.domain.product.entity.QProduct;
import com.trendhub.trendhub.domain.product.entity.QProductOption;
import com.trendhub.trendhub.domain.review.dto.MypageReviewDto;
import com.trendhub.trendhub.domain.review.entity.QReview;
import com.trendhub.trendhub.domain.review.entity.Review;
import com.trendhub.trendhub.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.trendhub.trendhub.domain.orderDetail.entity.QOrderDetail.*;
import static com.trendhub.trendhub.domain.orders.entity.QOrders.*;
import static com.trendhub.trendhub.domain.product.entity.QProduct.*;
import static com.trendhub.trendhub.domain.product.entity.QProductOption.*;
import static com.trendhub.trendhub.domain.review.entity.QReview.*;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<MypageReviewDto> findByUser(User user) {
        List<MypageReviewDto> result = jpaQueryFactory
                .selectDistinct(
                        Projections.constructor(
                                MypageReviewDto.class,
                                review.reviewId,
                                product.image,
                                product.name.as("title"),
                                review.content,
                                orders.date,
                                productOption.color,
                                productOption.size
                        )
                )
                .from(review)
                .innerJoin(product).on(review.product.productId.eq(product.productId))
                .innerJoin(orderDetail).on(orderDetail.product.productId.eq(product.productId))
                .innerJoin(productOption).on(orderDetail.productOption.productOptionId.eq(productOption.productOptionId))
                .innerJoin(orders).on(orderDetail.order.ordersId.eq(orders.ordersId))
                .where(review.user.eq(user))
                .orderBy(review.reviewId.desc())
                .fetch();

        return result;
    }
}
