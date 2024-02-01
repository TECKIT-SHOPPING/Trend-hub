package com.trendhub.trendhub.domain.orderDetail.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderDetail is a Querydsl query type for OrderDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderDetail extends EntityPathBase<OrderDetail> {

    private static final long serialVersionUID = 897284320L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderDetail orderDetail = new QOrderDetail("orderDetail");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final com.trendhub.trendhub.domain.orders.entity.QOrders order;

    public final NumberPath<Long> orderDetailId = createNumber("orderDetailId", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final com.trendhub.trendhub.domain.product.entity.QProduct product;

    public QOrderDetail(String variable) {
        this(OrderDetail.class, forVariable(variable), INITS);
    }

    public QOrderDetail(Path<? extends OrderDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderDetail(PathMetadata metadata, PathInits inits) {
        this(OrderDetail.class, metadata, inits);
    }

    public QOrderDetail(Class<? extends OrderDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new com.trendhub.trendhub.domain.orders.entity.QOrders(forProperty("order"), inits.get("order")) : null;
        this.product = inits.isInitialized("product") ? new com.trendhub.trendhub.domain.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

