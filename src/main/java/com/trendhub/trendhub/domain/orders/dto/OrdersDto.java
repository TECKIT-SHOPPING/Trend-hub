package com.trendhub.trendhub.domain.orders.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class OrdersDto {
    private Long ordersId;
    private Date date;
    private String status;
    private String name;
    private String address1; // 주소
    private String address2; // 상세주소
    private String zipcode;
    private String request; // 요청사항
    private String phone;
    private Long price;
}