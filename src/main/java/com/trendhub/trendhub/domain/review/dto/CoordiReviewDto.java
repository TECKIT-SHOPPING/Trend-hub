package com.trendhub.trendhub.domain.review.dto;

import com.trendhub.trendhub.domain.coordi.entity.Coordi;
import com.trendhub.trendhub.domain.review.entity.Review;
import com.trendhub.trendhub.domain.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CoordiReviewDto {
    private String content;
    private long reviewId;
    private LocalDateTime date;

    public Review toEntity(User user, Coordi coordi) {
        return Review.builder()
                .reviewId(this.reviewId)
                .content(this.content)
                .date(LocalDateTime.now())
                .user(user)
                .coordi(coordi)
                .build();
    }
}