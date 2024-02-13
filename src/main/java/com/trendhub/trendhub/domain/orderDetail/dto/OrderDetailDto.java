package com.trendhub.trendhub.domain.orderDetail.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
    private Long orderDetailId;
    private String productName;
    private String productImage;
    private String productOptionColor;
    private int count;
    private int price;
}