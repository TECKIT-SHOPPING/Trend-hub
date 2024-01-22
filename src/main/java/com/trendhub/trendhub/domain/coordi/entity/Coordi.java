package com.trendhub.trendhub.domain.coordi.entity;

import com.trendhub.trendhub.domain.user.entity.User;
import com.trendhub.trendhub.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coordi extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coordiId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String image;
    private int totalLike; // 좋아요 총 갯수
}
