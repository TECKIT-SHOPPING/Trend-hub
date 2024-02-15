package com.trendhub.trendhub.domain.orders.controller;

import com.trendhub.trendhub.domain.orders.dto.OrderPayInfo;
import com.trendhub.trendhub.domain.orders.entity.Orders;
import com.trendhub.trendhub.domain.orders.service.OrderService;
import com.trendhub.trendhub.domain.user.entity.User;
import com.trendhub.trendhub.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class ApiOrderController {
    private final OrderService orderService;
    private final Rq rq;

    @ResponseBody
    @PostMapping(value="/{id}")
    public void saveOrderUserInfo(@PathVariable("id") long id, @RequestBody OrderPayInfo orderPayInfo) {
        Orders order = orderService.findById(id).orElse(null);

        if (order == null) {
            throw new IllegalArgumentException("존재하지 않는 주문입니다.");
        }

        User user = rq.getUserInfo();

        if (!orderService.checkOrderAccess(user, order)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        orderService.saveOrderPayInfo(order, orderPayInfo);
    }
}
