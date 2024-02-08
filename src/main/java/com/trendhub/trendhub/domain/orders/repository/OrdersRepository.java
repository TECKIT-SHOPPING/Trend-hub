package com.trendhub.trendhub.domain.orders.repository;

import com.trendhub.trendhub.domain.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser_UserId(Long userId);
}