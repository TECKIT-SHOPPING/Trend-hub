/*
package com.trendhub.trendhub.domain.review.dto;

package com.trendhub.trendhub.domain.product.dto;

import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.product.entity.QnA;
import com.trendhub.trendhub.domain.user.entity.User;
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
    private LocalDateTime date;
    private String color;
    private String size;
    private Long reviewId;
    private String image;
    private Integer star;
    private Integer height;
    private Integer weight;
    private String gender;

    public QnA toEntity(Product product, User user) {
        return QnA.builder()
                .title(this.title)
                .inquireType(this.inquireType)
                .content(this.getContent())
                .createDate(LocalDateTime.now())
                .product(product)
                .user(user)
                .loginId(user.getLoginId())
                .image(product.getImage())
                .name(product.getName())
                .price(product.getPrice())
                .secret(this.secret)
                .build();
    }
}*/
