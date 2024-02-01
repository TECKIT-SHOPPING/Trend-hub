package com.trendhub.trendhub.domain.likes.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikes is a Querydsl query type for Likes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikes extends EntityPathBase<Likes> {

    private static final long serialVersionUID = -1070581120L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikes likes = new QLikes("likes");

    public final com.trendhub.trendhub.domain.coordi.entity.QCoordi coordi;

    public final NumberPath<Long> likesId = createNumber("likesId", Long.class);

    public final com.trendhub.trendhub.domain.product.entity.QProduct product;

    public final com.trendhub.trendhub.domain.user.entity.QUser user;

    public QLikes(String variable) {
        this(Likes.class, forVariable(variable), INITS);
    }

    public QLikes(Path<? extends Likes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikes(PathMetadata metadata, PathInits inits) {
        this(Likes.class, metadata, inits);
    }

    public QLikes(Class<? extends Likes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.coordi = inits.isInitialized("coordi") ? new com.trendhub.trendhub.domain.coordi.entity.QCoordi(forProperty("coordi"), inits.get("coordi")) : null;
        this.product = inits.isInitialized("product") ? new com.trendhub.trendhub.domain.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
        this.user = inits.isInitialized("user") ? new com.trendhub.trendhub.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

