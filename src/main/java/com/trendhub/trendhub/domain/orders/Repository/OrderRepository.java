package com.trendhub.trendhub.domain.orders.Repository;

import com.trendhub.trendhub.domain.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
