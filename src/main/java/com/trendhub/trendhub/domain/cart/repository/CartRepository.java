package com.trendhub.trendhub.domain.cart.repository;

import com.trendhub.trendhub.domain.cart.entity.Cart;
import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    void deleteByUserAndProduct(User userInfo, Product product);

    List<Cart> findByUser(User userInfo);

    boolean existsByUserAndProduct(User userInfo, Product product);
}
