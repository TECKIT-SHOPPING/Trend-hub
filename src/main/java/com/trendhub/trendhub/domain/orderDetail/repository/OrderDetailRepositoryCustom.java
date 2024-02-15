package com.trendhub.trendhub.domain.orderDetail.repository;

import com.trendhub.trendhub.domain.orderDetail.entity.OrderDetail;
import com.trendhub.trendhub.domain.user.entity.User;

import java.util.List;

public interface OrderDetailRepositoryCustom {

    List<OrderDetail> findByOrderAndProduct(User user, Long productId);

}
