package com.trendhub.trendhub.domain.review.dto;

import com.trendhub.trendhub.domain.coordi.entity.Coordi;
import com.trendhub.trendhub.domain.review.entity.Review;
import com.trendhub.trendhub.domain.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class CoordiReviewDto {
    private String content;

    public static Review toEntity(User user, Coordi coordi, String content) {
        return Review.builder()
                .content(content)
                .date(LocalDateTime.now())
                .user(user)
                .coordi(coordi)
                .build();
    }
}