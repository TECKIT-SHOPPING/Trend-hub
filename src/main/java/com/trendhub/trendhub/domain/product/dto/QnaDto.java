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
public class QnaDto {
    @Size(
            min = 20, max = 200, message = "최소 20글자, 최대 200글자 입력해주세요."

    )
    private String content;

    @Size(
            min = 5, max = 15, message = "최소 5글자, 최대 20글자 입력해주세요."

    )
    private String title;

    private String inquireType;
    private String name;
    private String image;
    private int price;

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
                .build();
    }
}