package com.trendhub.trendhub.domain.order.controller;


import com.trendhub.trendhub.domain.order.entity.Order;
import com.trendhub.trendhub.domain.order.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.getOrders();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/{id}")
    public String getOrder(@PathVariable Long id, Model model) {
        Order order = orderService.getOrder(id).orElse(null);
        model.addAttribute("order", order);
        return "order";
    }

    @GetMapping("/users/{userId}")
    public String getOrdersByUser(@PathVariable Long userId, Model model) {
        List<Order> orders = orderService.getOrdersByUser(userId);
        model.addAttribute("orders", orders);
        return "orders";
    }
}