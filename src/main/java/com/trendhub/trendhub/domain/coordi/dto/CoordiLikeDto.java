package com.trendhub.trendhub.domain.coordi.dto;

import com.trendhub.trendhub.domain.coordi.entity.Coordi;
import com.trendhub.trendhub.domain.likes.entity.Likes;
import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.user.entity.User;
import lombok.Data;

@Data
public class CoordiLikeDto {

    private Long coordiId;

    public Likes toEntity(User user, Coordi coordi) {
        return Likes.builder()
                .coordi(coordi)
                .user(user)
                .build();
    }
}
