package com.trendhub.trendhub.domain.orderDetail.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetailDto {
    private Long orderDetailId;
    private String productName;
    private String productImage;
    private String productOption;
    private int quantity;
    private int price;

    // 해당 생성자를 추가
    public OrderDetailDto(Long orderDetailId, String productName, String productImage, String productOption, int quantity, int price) {
        this.orderDetailId = orderDetailId;
        this.productName = productName;
        this.productImage = productImage;
        this.productOption = productOption;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
}