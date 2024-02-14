package com.trendhub.trendhub.domain.cart.service;

import com.trendhub.trendhub.domain.cart.entity.Cart;
import com.trendhub.trendhub.domain.cart.repository.CartRepository;
import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;

    public List<Cart> getCartList(User userInfo) {
        return cartRepository.findByUser(userInfo);
    }

    @Transactional
    public void addCartProduct(User userInfo, Product product) {
        if (isExistCartProduct(userInfo, product)) {
            throw new IllegalArgumentException("이미 장바구니에 담긴 상품입니다.");
        }

        Cart cart = Cart.builder()
                .user(userInfo)
                .product(product)
                .build();

        cartRepository.save(cart);
    }

    @Transactional
    public void removeCartProduct(User userInfo, Product product) {
        cartRepository.deleteByUserAndProduct(userInfo, product);
    }

    public boolean isExistCartProduct(User userInfo, Product product) {
        return cartRepository.existsByUserAndProduct(userInfo, product);
    }

    public List<Cart> findByUser(User userInfo) {
        return cartRepository.findByUser(userInfo);
    }

    public void delete(Cart cart) {
        cartRepository.delete(cart);
    }
}
