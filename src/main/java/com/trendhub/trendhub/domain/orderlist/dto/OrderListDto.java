package com.trendhub.trendhub.domain.orderlist.dto;


import com.trendhub.trendhub.domain.orderDetail.dto.OrderDetailDto;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data

@NoArgsConstructor
public class OrderListDto {
    private Long orderId;
    private Date orderDate;
    private Double totalPrice;
    private String status;
    private List<OrderDetailDto> orderDetails;

    // Constructor, getters and setters

    public OrderListDto(Long orderId, Date orderDate, Double totalPrice, String status, List<OrderDetailDto> orderDetails) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderDetails = orderDetails;
    }

    // Getters and Setters
}