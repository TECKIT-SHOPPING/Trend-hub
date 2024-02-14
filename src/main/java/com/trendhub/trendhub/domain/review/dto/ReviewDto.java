package com.trendhub.trendhub.domain.review.dto;

import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.review.entity.Review;
import com.trendhub.trendhub.domain.user.entity.User;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewDto {
    @Size(
            min = 20, max = 500, message = "20자 이상 작성해주세요."

    )
    private String content;
    @Digits(integer = 3, fraction = 0, message = "숫자 3자리만 입력 가능합니다.")
    private Integer height;
    @Digits(integer = 3, fraction = 0, message = "숫자 3자리만 입력 가능합니다.")
    private Integer weight;

    private LocalDateTime date;
    private String color;
    private String size;
    private Long reviewId;
    private String image;
    private Integer star;
    private String gender;

    public Review toEntity(User user, Product product) {
        return Review.builder()
                .reviewId(this.reviewId)
                .star(this.star)
                .content(this.content)
                .gender(this.gender)
                .date(LocalDateTime.now())
                .user(user)
                .height(this.height)
                .weight(this.weight)
                .product(product)
                .build();
    }
}
