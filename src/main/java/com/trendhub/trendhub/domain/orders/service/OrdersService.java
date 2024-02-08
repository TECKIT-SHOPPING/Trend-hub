package com.trendhub.trendhub.domain.orders.service;

import com.trendhub.trendhub.domain.orders.dto.OrdersDto;
import com.trendhub.trendhub.domain.orders.entity.Orders;
import com.trendhub.trendhub.domain.orders.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrdersService {
    private final OrdersRepository ordersRepository;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public List<OrdersDto> getUserOrders(Long userId) {
        List<Orders> orders = ordersRepository.findByUser_UserId(userId);
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private OrdersDto convertToDto(Orders orders) {
        OrdersDto ordersDto = new OrdersDto();
        ordersDto.setOrdersId(orders.getOrdersId());
        ordersDto.setDate(orders.getDate());
        ordersDto.setStatus(orders.getStatus());
        ordersDto.setName(orders.getName());
        ordersDto.setAddress1(orders.getAddress1());
        ordersDto.setAddress2(orders.getAddress2());
        ordersDto.setZipcode(orders.getZipcode());
        ordersDto.setRequest(orders.getRequest());
        ordersDto.setPhone(orders.getPhone());
        ordersDto.setPrice(orders.getPrice());
        return ordersDto;
    }
}