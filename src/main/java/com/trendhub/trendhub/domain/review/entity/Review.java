package com.trendhub.trendhub.domain.review.entity;

import com.trendhub.trendhub.domain.coordi.entity.Coordi;
import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.user.entity.User;
import com.trendhub.trendhub.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordi_id")
    private Coordi coordi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;
    private LocalDateTime date;
    private String gender;
    private Double height;
    private Double weight;
    private String image;

    // 별점
//    @Column(name = "star")
    private Integer star;

    /*@AssertTrue(message = "하나 이상의 별을 선택해주세요.")
    public boolean isStarSelected() {
        return star != null && star > 0;
    }*/
}
