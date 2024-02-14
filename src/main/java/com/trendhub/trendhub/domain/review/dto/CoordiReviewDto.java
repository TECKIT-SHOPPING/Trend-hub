package com.trendhub.trendhub.domain.review.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoordiReviewDto {
    private String content;
    private long reviewId;

    public CoordiReviewDto(String content, long reviewId) {
        this.content = content;
        this.reviewId = reviewId;
    }
}