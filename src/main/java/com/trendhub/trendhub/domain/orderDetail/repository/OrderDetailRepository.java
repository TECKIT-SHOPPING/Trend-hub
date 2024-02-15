package com.trendhub.trendhub.domain.orderDetail.repository;

import com.trendhub.trendhub.domain.orderDetail.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>, OrderDetailRepositoryCustom {
}
