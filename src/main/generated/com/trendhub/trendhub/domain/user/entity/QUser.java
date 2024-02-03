package com.trendhub.trendhub.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 602846320L;

    public static final QUser user = new QUser("user");

    public final com.trendhub.trendhub.global.entity.QBaseTimeEntity _super = new com.trendhub.trendhub.global.entity.QBaseTimeEntity(this);

    public final StringPath address1 = createString("address1");

    public final StringPath address2 = createString("address2");

    public final DateTimePath<java.time.LocalDateTime> agreeAge = createDateTime("agreeAge", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> agreeEmail = createDateTime("agreeEmail", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> agreeInfo = createDateTime("agreeInfo", java.time.LocalDateTime.class);

    public final DatePath<java.util.Date> birth = createDate("birth", java.util.Date.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    public final StringPath email = createString("email");

    public final BooleanPath emailAuthChecked = createBoolean("emailAuthChecked");

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final StringPath loginId = createString("loginId");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final StringPath profile = createString("profile");

    public final EnumPath<SocialProvider> provider = createEnum("provider", SocialProvider.class);

    public final StringPath providerId = createString("providerId");

    public final StringPath role = createString("role");

    public final StringPath status = createString("status");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateAt = _super.updateAt;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath username = createString("username");

    public final StringPath zipcode = createString("zipcode");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

