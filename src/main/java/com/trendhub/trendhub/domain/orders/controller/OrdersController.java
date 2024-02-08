package com.trendhub.trendhub.domain.orders.controller;

import com.trendhub.trendhub.domain.orders.dto.OrdersDto;
import com.trendhub.trendhub.domain.orders.entity.Orders;
import com.trendhub.trendhub.domain.orders.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping("/users/{userId}/orders")
    public String getUserOrders(@PathVariable Long userId, Model model) {
        List<OrdersDto> orders = ordersService.getUserOrders(userId);
        model.addAttribute("orders", orders);
        return "orders";
    }
}