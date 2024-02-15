package com.trendhub.trendhub.domain.product.entity;

import com.trendhub.trendhub.domain.likes.entity.Likes;
import com.trendhub.trendhub.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_category_id")
    private MainCategory mainCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id")
    private Season season;

    @OneToMany(mappedBy = "product")
    private List<ProductOption> productOptionList = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<Likes> likes = new ArrayList<>();

    private String name;
    private String image;
    private int price;
    private int discount;
    private String detailContent;
    private String detailImage;
    private int totalLike;
    private int viewCount;

    //연관관계 메서드
    public void unLikeProduct(Likes likes) {
        this.likes.remove(likes);
        likes.removeProduct();
        this.totalLike = this.totalLike - 1;
    }

    public void likeProduct(Likes likes) {
        this.likes.add(likes);
        likes.addProduct(this);
        this.totalLike = this.totalLike + 1;
    }
    public void addViewCount() {
        this.viewCount += 1;
    }
}
