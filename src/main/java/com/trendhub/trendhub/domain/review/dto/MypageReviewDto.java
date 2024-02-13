package com.trendhub.trendhub.domain.review.dto;

import com.trendhub.trendhub.domain.orderDetail.entity.OrderDetail;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class MypageReviewDto {

    private Long reviewId;
    private String image;
    private String title;
    private String content;
    private Date date;
    private String color;
    private int size;


    public MypageReviewDto(Long reviewId, String image, String title, String content, Date date, String color, int size) {
        this.reviewId = reviewId;
        this.image = image;
        this.title = title;
        this.content = content;
        this.date = date;
        this.color = color;
        this.size = size;
    }
}
