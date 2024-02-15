package com.trendhub.trendhub.domain.product.repository;

import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trendhub.trendhub.domain.product.dto.ProductDto;
import com.trendhub.trendhub.domain.product.entity.Season;
import com.trendhub.trendhub.domain.user.entity.User;
import com.trendhub.trendhub.global.config.querydsl.QuerydslUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.trendhub.trendhub.domain.likes.entity.QLikes.*;
import static com.trendhub.trendhub.domain.product.entity.QBrand.*;
import static com.trendhub.trendhub.domain.product.entity.QProduct.*;
import static org.springframework.util.ObjectUtils.isEmpty;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ProductDto> findTop10ByOrderByViewCountDesc(User user) {
        List<ProductDto> result = jpaQueryFactory
                .select(Projections.constructor(ProductDto.class,
                        product.productId,
                        product.image,
                        product.name,
                        product.price,
                        product.discount,
                        product.totalLike,
                        new CaseBuilder()
                                .when(likes.likesId.isNotNull()).then(true)
                                .otherwise(false).as("liked")
                ))
                .from(product)
                .leftJoin(likes)
                .on(likes.product.eq(product).and(likes.user.eq(user)))
                .orderBy(product.viewCount.desc())
                .limit(10)
                .fetch();
        return result;
    }

    @Override
    public List<ProductDto> findTop10ByOrderByViewCountDescAnonymousUser() {
        List<ProductDto> result = jpaQueryFactory
                .select(Projections.constructor(ProductDto.class,
                        product.productId,
                        product.image,
                        product.name,
                        product.price,
                        product.discount,
                        product.totalLike,
                        Expressions.asBoolean(false).as("liked")
                ))
                .from(product)
                .orderBy(product.viewCount.desc())
                .limit(10)
                .fetch();
        return result;
    }

    @Override
    public List<ProductDto> findByLikesProducts(User user) {
        List<ProductDto> result = jpaQueryFactory
                .select(Projections.constructor(ProductDto.class,
                        product.productId,
                        product.image,
                        product.name,
                        product.price,
                        product.discount,
                        product.totalLike,
                        new CaseBuilder()
                                .when(likes.likesId.isNotNull()).then(true)
                                .otherwise(false).as("liked")
                ))
                .from(product)
                .innerJoin(likes)
                .on(likes.product.eq(product).and(likes.user.eq(user)))
                .orderBy(product.viewCount.desc())
                .fetch();
        return result;
    }

    @Override
    public List<ProductDto> findByRecentlyProductsIn(User user, List<Long> productIdList) {
        List<ProductDto> result = jpaQueryFactory
                .select(Projections.constructor(ProductDto.class,
                        product.productId,
                        product.image,
                        product.name,
                        product.price,
                        product.discount,
                        product.totalLike,
                        new CaseBuilder()
                                .when(likes.likesId.isNotNull()).then(true)
                                .otherwise(false).as("liked")
                ))
                .from(product)
                .leftJoin(likes)
                .on(likes.product.eq(product).and(likes.user.eq(user)))
                .where(product.productId.in(productIdList))
                .fetch();
        return result;
    }

    @Override
    public Page<ProductDto> categoryProductList(Long mainCategory, Long subCategory, User user, Pageable pageable) {
        List<OrderSpecifier> ORDERS = productSort(pageable);
        List<ProductDto> content;
        if (user == null) {
            System.out.println("유저가 null");
            content = jpaQueryFactory
                    .select(Projections.constructor(ProductDto.class,
                            product.productId,
                            product.image,
                            product.name,
                            product.price,
                            product.discount,
                            product.totalLike,
                            Expressions.asBoolean(false).as("liked")
                    ))
                    .from(product)
                    .where(categoryEq(mainCategory, subCategory))
                    .offset(pageable.getOffset())
                    .orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
                    .limit(pageable.getPageSize())
                    .fetch();
        } else {
            System.out.println("유저가 null이 아님");
            content = jpaQueryFactory
                    .select(Projections.constructor(ProductDto.class,
                            product.productId,
                            product.image,
                            product.name,
                            product.price,
                            product.discount,
                            product.totalLike,
                            new CaseBuilder()
                                    .when(likes.likesId.isNotNull()).then(true)
                                    .otherwise(false).as("liked")
                    ))
                    .from(product)
                    .leftJoin(likes)
                    .on(likes.product.eq(product).and(likes.user.eq(user)))
                    .where(categoryEq(mainCategory, subCategory))
                    .offset(pageable.getOffset())
                    .orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
                    .limit(pageable.getPageSize())
                    .fetch();
        }


        int total = jpaQueryFactory
                .selectFrom(product)
                .from(product)
                .where(categoryEq(mainCategory, subCategory))
                .fetch()
                .size();


        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<ProductDto> searchProductList(String keyword, User user, Pageable pageable) {
        List<OrderSpecifier> ORDERS = productSort(pageable);
        List<ProductDto> content;
        if (user == null) {
            System.out.println("유저가 null");
            content = jpaQueryFactory
                    .select(Projections.constructor(ProductDto.class,
                            product.productId,
                            product.image,
                            product.name,
                            product.price,
                            product.discount,
                            product.totalLike,
                            Expressions.asBoolean(false).as("liked")
                    ))
                    .from(product)
                    .leftJoin(product.brand, brand)
                    .on(brand.eq(product.brand))
                    .where(product.name.like("%" + keyword + "%").or(brand.name.like("%" + keyword + "%")))
                    .offset(pageable.getOffset())
                    .orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
                    .limit(pageable.getPageSize())
                    .fetch();
        } else {
            System.out.println("유저가 null이 아님");
            content = jpaQueryFactory
                    .select(Projections.constructor(ProductDto.class,
                            product.productId,
                            product.image,
                            product.name,
                            product.price,
                            product.discount,
                            product.totalLike,
                            new CaseBuilder()
                                    .when(likes.likesId.isNotNull()).then(true)
                                    .otherwise(false).as("liked")
                    ))
                    .from(product)
                    .leftJoin(likes)
                    .on(likes.product.eq(product).and(likes.user.eq(user)))
                    .leftJoin(product.brand, brand)
                    .on(brand.eq(product.brand))
                    .where(product.name.like("%" + keyword + "%").or(brand.name.like("%" + keyword + "%")))
                    .offset(pageable.getOffset())
                    .orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
                    .limit(pageable.getPageSize())
                    .fetch();
        }


        int total = jpaQueryFactory
                .selectFrom(product)
                .from(product)
                .where(product.name.like("%" + keyword + "%").or(brand.name.like("%" + keyword + "%")))
                .fetch()
                .size();


        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression categoryEq(Long mainCategory, Long subCategory) {
        return product.mainCategory.mainCategoryId.eq(mainCategory).and(product.subCategory.subCategoryId.eq(subCategory));
    }


    private List<OrderSpecifier> productSort(Pageable pageable) {

        List<OrderSpecifier> ORDERS = new ArrayList<>();

        if (!isEmpty(pageable.getSort()) && pageable.getSort().isSorted()) {
            for (Sort.Order order : pageable.getSort()) {
                System.out.println("order.getProperty() = " + order.getProperty());
                switch (order.getProperty()) {
                    case "popular":
                        OrderSpecifier<?> viewCountDesc = QuerydslUtil.getSortedColumn(Order.DESC, product, "totalLike");
                        ORDERS.add(viewCountDesc);
                        break;
                    case "low-price":
                        OrderSpecifier<?> priceAsc = QuerydslUtil.getSortedColumn(Order.ASC, product, "price");
                        ORDERS.add(priceAsc);
                        break;
                    case "high-price":
                        OrderSpecifier<?> priceDesc = QuerydslUtil.getSortedColumn(Order.DESC, product, "price");
                        ORDERS.add(priceDesc);
                        break;
                    case "discount":
                        OrderSpecifier<?> discountDesc = QuerydslUtil.getSortedColumn(Order.DESC, product, "discount");
                        ORDERS.add(discountDesc);
                        break;
                    default:
                        break;
                }
            }
        } else {
            OrderSpecifier<?> viewCountDesc = QuerydslUtil.getSortedColumn(Order.DESC, product, "viewCount");
            ORDERS.add(viewCountDesc);
        }

        return ORDERS;
    }

    @Override
    public List<ProductDto> findTop20ByOrderByCreateMonthDesc(User user) {
        List<ProductDto> result;
        if (user == null) {
            result = jpaQueryFactory
                    .select(Projections.constructor(ProductDto.class,
                            product.productId,
                            product.image,
                            product.name,
                            product.price,
                            product.discount,
                            product.totalLike,
                            Expressions.asBoolean(false).as("liked")
                    ))
                    .from(product)
                    .where(product.discount.between(0, 40))
                    .orderBy(product.createAt.month().desc())
                    .limit(20)
                    .fetch();
        } else {
            result = jpaQueryFactory
                    .select(Projections.constructor(ProductDto.class,
                            product.productId,
                            product.image,
                            product.name,
                            product.price,
                            product.discount,
                            product.totalLike,
                            new CaseBuilder()
                                    .when(likes.likesId.isNotNull()).then(true)
                                    .otherwise(false).as("liked")
                    ))
                    .from(product)
                    .leftJoin(likes)
                    .on(likes.product.eq(product).and(likes.user.eq(user)))
                    .where(product.discount.between(0, 40))
                    .orderBy(product.createAt.month().desc())
                    .limit(20)
                    .fetch();

        }
        return result;
    }

    @Override
    public List<ProductDto> totalLikeTop20(User user) {
        List<ProductDto> result;
        if (user == null) {
            result = jpaQueryFactory
                    .select(Projections.constructor(ProductDto.class,
                            product.productId,
                            product.image,
                            product.name,
                            product.price,
                            product.discount,
                            product.totalLike,
                            Expressions.asBoolean(false).as("liked")
                    ))
                    .from(product)
                    .where(product.discount.between(0, 40))
                    .orderBy(product.totalLike.desc())
                    .limit(20)
                    .fetch();
        } else {
            result = jpaQueryFactory
                    .select(Projections.constructor(ProductDto.class,
                            product.productId,
                            product.image,
                            product.name,
                            product.price,
                            product.discount,
                            product.totalLike,
                            new CaseBuilder()
                                    .when(likes.likesId.isNotNull()).then(true)
                                    .otherwise(false).as("liked")
                    ))
                    .from(product)
                    .leftJoin(likes)
                    .on(likes.product.eq(product).and(likes.user.eq(user)))
                    .where(product.discount.between(0, 40))
                    .orderBy(product.totalLike.desc())
                    .limit(20)
                    .fetch();
        }
        return result;
    }

    @Override
    public List<ProductDto> findByFWsale(User user) {
        List<ProductDto> result;
        if (user == null) {
            result = jpaQueryFactory
                    .select(Projections.constructor(ProductDto.class,
                            product.productId,
                            product.image,
                            product.name,
                            product.price,
                            product.discount,
                            product.totalLike,
                            Expressions.asBoolean(false).as("liked")
                    ))
                    .from(product)
                    .where(product.season.seasonId.eq(2L).and(product.discount.between(0, 40)))
                    .orderBy(product.createAt.month().desc())
                    .limit(20)
                    .fetch();
        } else {
            result = jpaQueryFactory
                    .select(Projections.constructor(ProductDto.class,
                            product.productId,
                            product.image,
                            product.name,
                            product.price,
                            product.discount,
                            product.totalLike,
                            new CaseBuilder()
                                    .when(likes.likesId.isNotNull()).then(true)
                                    .otherwise(false).as("liked")
                    ))
                    .from(product)
                    .leftJoin(likes)
                    .on(likes.product.eq(product).and(likes.user.eq(user)))
                    .where(product.season.seasonId.eq(2L).and(product.discount.between(0, 40)))
                    .orderBy(product.createAt.month().desc())
                    .limit(20)
                    .fetch();
        }
        return result;
    }

    @Override
    public ProductDto findByUserCheckLike(User user, Long productId) {
        ProductDto result = jpaQueryFactory
                .select(Projections.constructor(ProductDto.class,
                        product.productId,
                        product.image,
                        product.name,
                        product.price,
                        product.discount,
                        product.totalLike,
                        new CaseBuilder()
                                .when(likes.likesId.isNotNull()).then(true)
                                .otherwise(false).as("liked")
                ))
                .from(product)
                .leftJoin(likes)
                .on(likes.product.eq(product).and(likes.user.eq(user)))
                .where(product.productId.eq(productId))
                .fetchOne();
        return result;
    }


    @Override
    public ProductDto findByUserCheckLikeAnonymousUser(Long productId) {
        ProductDto result = jpaQueryFactory
                .select(Projections.constructor(ProductDto.class,
                        product.productId,
                        product.image,
                        product.name,
                        product.price,
                        product.discount,
                        product.totalLike,
                        Expressions.asBoolean(false).as("liked")
                ))
                .from(product)
                .where(product.productId.eq(productId))
                .fetchOne();
        return result;
    }
}