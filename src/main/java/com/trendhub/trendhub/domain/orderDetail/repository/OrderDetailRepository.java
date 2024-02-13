package com.trendhub.trendhub.domain.orderDetail.repository;

import com.trendhub.trendhub.domain.orderDetail.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    // 사용자 ID를 통해 OrderDetail 목록을 조회
    @Query("SELECT o FROM OrderDetail o WHERE o.order.user.userId = :userId")
    List<OrderDetail> findByUserId(@Param("userId") Long userId);

    // 주문 ID를 통해 OrderDetail 목록을 조회
    @Query("SELECT o FROM OrderDetail o WHERE o.order.orderId = :orderId")
    List<OrderDetail> findByOrderId(@Param("orderId") Long orderId);
}