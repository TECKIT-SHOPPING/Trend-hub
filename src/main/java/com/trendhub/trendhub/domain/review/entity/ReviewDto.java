package com.trendhub.trendhub.domain.review.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDto {
    private Long reviewId;
    private Long coordiId;
    private Long productId;
    private String content;
    private int star;

    public ReviewDto(Long reviewId, Long coordiId, Long productId, String content, int star) {
        this.reviewId = reviewId;
        this.coordiId = coordiId;
        this.productId = productId;
        this.content = content;
        this.star = star;
    }
}