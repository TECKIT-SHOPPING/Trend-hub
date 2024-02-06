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
            min = 10, max = 50, message = "최소 10글자, 최대 50글자 입력해주세요."

    )
    private String title;

    private String inquireType;

    public QnA toEntity(Product product, User user) {
        return QnA.builder()
                .content(this.getContent())
                .createDate(LocalDateTime.now())
                .product(product)
                .user(user)
                .loginId(user.getLoginId())
                .build();
    }
}
