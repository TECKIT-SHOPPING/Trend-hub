package com.trendhub.trendhub.domain.orderDetail.controller;

import com.trendhub.trendhub.domain.orderDetail.service.OrderDetailService;
import com.trendhub.trendhub.domain.orderDetail.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("/user/{userId}/order-details")
    public String getOrderDetails(@PathVariable Long userId, Model model) {
        model.addAttribute("orderDetails", orderDetailService.getOrderDetailsByUserId(userId));
        return "orderDetails"; // Thymeleaf 뷰 템플릿 이름
    }
}
