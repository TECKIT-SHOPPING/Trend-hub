package com.trendhub.trendhub.domain.orders.Repository;

import com.trendhub.trendhub.domain.orders.entity.Orders;
import com.trendhub.trendhub.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser(User user);
}
