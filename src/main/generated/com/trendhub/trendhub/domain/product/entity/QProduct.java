package com.trendhub.trendhub.domain.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 1161658848L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final com.trendhub.trendhub.global.entity.QBaseTimeEntity _super = new com.trendhub.trendhub.global.entity.QBaseTimeEntity(this);

    public final QBrand brand;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    public final StringPath detailContent = createString("detailContent");

    public final StringPath detailImage = createString("detailImage");

    public final NumberPath<Integer> discount = createNumber("discount", Integer.class);

    public final StringPath image = createString("image");

    public final ListPath<com.trendhub.trendhub.domain.likes.entity.Likes, com.trendhub.trendhub.domain.likes.entity.QLikes> likes = this.<com.trendhub.trendhub.domain.likes.entity.Likes, com.trendhub.trendhub.domain.likes.entity.QLikes>createList("likes", com.trendhub.trendhub.domain.likes.entity.Likes.class, com.trendhub.trendhub.domain.likes.entity.QLikes.class, PathInits.DIRECT2);

    public final QMainCategory mainCategory;

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final ListPath<ProductOption, QProductOption> productOptionList = this.<ProductOption, QProductOption>createList("productOptionList", ProductOption.class, QProductOption.class, PathInits.DIRECT2);

    public final QSeason season;

    public final QSubCategory subCategory;

    public final NumberPath<Integer> totalLike = createNumber("totalLike", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateAt = _super.updateAt;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.brand = inits.isInitialized("brand") ? new QBrand(forProperty("brand")) : null;
        this.mainCategory = inits.isInitialized("mainCategory") ? new QMainCategory(forProperty("mainCategory")) : null;
        this.season = inits.isInitialized("season") ? new QSeason(forProperty("season")) : null;
        this.subCategory = inits.isInitialized("subCategory") ? new QSubCategory(forProperty("subCategory")) : null;
    }

}

