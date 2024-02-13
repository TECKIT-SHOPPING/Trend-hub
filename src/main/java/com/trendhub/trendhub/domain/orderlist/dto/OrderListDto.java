package com.trendhub.trendhub.domain.orderlist.dto;


import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListDto {
    private Long orderId;
    private String status;
    private String name;
    private Long price;
    // 추가적인 필드 및 생성자 등은 필요에 따라 구현
}