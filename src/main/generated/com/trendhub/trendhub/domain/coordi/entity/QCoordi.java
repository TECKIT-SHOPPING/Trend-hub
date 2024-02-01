package com.trendhub.trendhub.domain.coordi.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCoordi is a Querydsl query type for Coordi
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoordi extends EntityPathBase<Coordi> {

    private static final long serialVersionUID = -1332551486L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCoordi coordi = new QCoordi("coordi");

    public final com.trendhub.trendhub.global.entity.QBaseTimeEntity _super = new com.trendhub.trendhub.global.entity.QBaseTimeEntity(this);

    public final NumberPath<Long> coordiId = createNumber("coordiId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    public final StringPath image = createString("image");

    public final NumberPath<Integer> totalLike = createNumber("totalLike", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateAt = _super.updateAt;

    public final com.trendhub.trendhub.domain.user.entity.QUser user;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QCoordi(String variable) {
        this(Coordi.class, forVariable(variable), INITS);
    }

    public QCoordi(Path<? extends Coordi> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCoordi(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCoordi(PathMetadata metadata, PathInits inits) {
        this(Coordi.class, metadata, inits);
    }

    public QCoordi(Class<? extends Coordi> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.trendhub.trendhub.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

