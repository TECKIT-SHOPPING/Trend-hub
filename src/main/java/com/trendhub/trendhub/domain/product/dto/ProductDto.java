package com.trendhub.trendhub.domain.product.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {
    private long productId;
    private String image;
    private String name;
    private int price;
    private int totalLike;
    private Long likeId;
    private boolean liked;

    public ProductDto(long productId, String image, String name, int price, int totalLike, Long likeId, boolean liked) {
        this.productId = productId;
        this.image = image;
        this.name = name;
        this.price = price;
        this.totalLike = totalLike;
        this.likeId = likeId;
        this.liked = liked;
    }

}