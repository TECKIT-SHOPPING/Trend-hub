package com.trendhub.trendhub.domain.product.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {
    private Long productId;
    private String image;
    private String name;
    private int price;
    private int discount;
    private int totalLike;
    private Boolean liked;

    public ProductDto(Long productId, String image, String name, int price, int discount, int totalLike, Boolean liked) {
        this.productId = productId;
        this.image = image;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.totalLike = totalLike;
        this.liked = liked;
    }

}