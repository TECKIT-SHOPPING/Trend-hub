package com.trendhub.trendhub.domain.review.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MypageReviewDto {

    private Long reviewId;
    private String image;
    private String title;
    private String content;
    private LocalDateTime date;
    private String color;
    private int size;


    public MypageReviewDto(Long reviewId, String image, String title, String content, LocalDateTime date, String color, int size) {
        this.reviewId = reviewId;
        this.image = image;
        this.title = title;
        this.content = content;
        this.date = date;
        this.color = color;
        this.size = size;
    }
}
