package com.trendhub.trendhub.domain.product.dto;

import com.trendhub.trendhub.domain.likes.entity.Likes;
import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.user.entity.User;
import lombok.Data;

@Data
public class ProductLikeDto {

    private Long productId;

    public Likes toEntity(User user, Product product) {
        return Likes.builder()
                .product(product)
                .user(user)
                .build();
    }
}
