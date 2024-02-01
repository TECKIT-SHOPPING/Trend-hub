package com.trendhub.trendhub.domain.cart.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCart is a Querydsl query type for Cart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCart extends EntityPathBase<Cart> {

    private static final long serialVersionUID = 1266711450L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCart cart = new QCart("cart");

    public final com.trendhub.trendhub.global.entity.QBaseTimeEntity _super = new com.trendhub.trendhub.global.entity.QBaseTimeEntity(this);

    public final NumberPath<Long> cartId = createNumber("cartId", Long.class);

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    public final com.trendhub.trendhub.domain.product.entity.QProduct product;

    public final com.trendhub.trendhub.domain.product.entity.QProductOption productOption;

    public final NumberPath<Integer> size = createNumber("size", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateAt = _super.updateAt;

    public final com.trendhub.trendhub.domain.user.entity.QUser user;

    public QCart(String variable) {
        this(Cart.class, forVariable(variable), INITS);
    }

    public QCart(Path<? extends Cart> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCart(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCart(PathMetadata metadata, PathInits inits) {
        this(Cart.class, metadata, inits);
    }

    public QCart(Class<? extends Cart> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.trendhub.trendhub.domain.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
        this.productOption = inits.isInitialized("productOption") ? new com.trendhub.trendhub.domain.product.entity.QProductOption(forProperty("productOption"), inits.get("productOption")) : null;
        this.user = inits.isInitialized("user") ? new com.trendhub.trendhub.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

