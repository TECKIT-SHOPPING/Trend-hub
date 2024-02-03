package com.trendhub.trendhub.domain.coordi.entity;

import com.trendhub.trendhub.domain.likes.entity.Likes;
import com.trendhub.trendhub.domain.review.entity.Review;
import com.trendhub.trendhub.domain.user.entity.User;
import com.trendhub.trendhub.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Coordi extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coordiId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String image;
    private int totalLike; // 좋아요 총 갯수
    private int viewCount;

    @OneToMany(mappedBy = "coordi")
    private List<Likes> likes = new ArrayList<>();


    //연관관계 메서드
    public void likeProduct(Likes likes) {
        this.likes.add(likes);
        likes.addCoordi(this);
        this.totalLike = this.totalLike + 1;
    }

    public void unLikeProduct(Likes likes) {
        this.likes.remove(likes);
        likes.removeCoordi();
        this.totalLike = this.totalLike - 1;
    }
}
