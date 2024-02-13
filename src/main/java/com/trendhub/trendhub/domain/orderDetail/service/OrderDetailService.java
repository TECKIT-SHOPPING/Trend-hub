package com.trendhub.trendhub.domain.orderDetail.service;

import com.trendhub.trendhub.domain.orderDetail.dto.OrderDetailDto;
import com.trendhub.trendhub.domain.orderDetail.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<OrderDetailDto> getOrderDetailsByUserId(Long userId) {
        // userId를 기반으로 주문 상세 정보를 찾고, OrderDetailDto 리스트로 변환합니다.
        return orderDetailRepository.findByUserId(userId).stream()
                .map(detail -> new OrderDetailDto(
                        detail.getOrderDetailId(),
                        detail.getProduct().getName(),
                        detail.getProduct().getImage(),
                        detail.getProductOption().getColor(),
                        detail.getCount(),
                        detail.getPrice()
                )).collect(Collectors.toList());
    }
}