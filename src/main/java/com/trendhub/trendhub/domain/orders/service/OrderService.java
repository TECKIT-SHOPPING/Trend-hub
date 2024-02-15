package com.trendhub.trendhub.domain.orders.service;

import com.trendhub.trendhub.domain.cart.entity.Cart;
import com.trendhub.trendhub.domain.cart.service.CartService;
import com.trendhub.trendhub.domain.orders.repository.OrderRepository;
import com.trendhub.trendhub.domain.orders.dto.OrderPayInfo;
import com.trendhub.trendhub.domain.orders.dto.OrderProductInfo;
import com.trendhub.trendhub.domain.orders.entity.Orders;
import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.product.entity.ProductOption;
import com.trendhub.trendhub.domain.product.repository.ProductOptionRepository;
import com.trendhub.trendhub.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductOptionRepository productOptionRepository;
    private final CartService cartService;

    @Transactional
    public Orders orderProduct(User userInfo, Product product) {
        Orders order = Orders.builder()
                .user(userInfo)
                .build();

        order.addProduct(product);

        orderRepository.save(order);

        return order;
    }

    @Transactional
    public Orders orderProduct(User userInfo, Product product, OrderProductInfo orderProductInfo) {
        long productOptionId = orderProductInfo.getProductOptionId();
        int amount = orderProductInfo.getAmount();

        Optional<ProductOption> productOption = productOptionRepository.findById(productOptionId);

        if (!productOption.isPresent()) {
           throw new IllegalArgumentException("존재하지 않는 상품 옵션입니다.");
        }

//        productOptionRepository.findById(productOptionId)
//                .ifPresent(productOption -> {
//                    if (productOption.getStock() < amount) {
//                        throw new IllegalArgumentException("재고가 부족합니다.");
//                    }
//                }

        Orders order = Orders.builder()
                .user(userInfo)
                .build();


        order.addProduct(product, amount, productOption.get());

        orderRepository.save(order);

        return order;
    }

    @Transactional
    public Orders orderProductFromCart(User userInfo) {
        List<Cart> cartItems = cartService.findByUser(userInfo);

        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalArgumentException("장바구니가 비어 있습니다.");
        }

        Orders order = Orders.builder()
                .user(userInfo)
                .build();

        cartItems.stream()
                .forEach(order::addItem);

        orderRepository.save(order);

        cartItems.stream()
                .forEach(cartService::delete);

        return order;
    }

    public Optional<Orders> findById(long id) {
        return orderRepository.findById(id);
    }

    public boolean checkOrderAccess(User user, Orders order) {
        return order.getUser().equals(user);
    }

    public void payDone(Orders order) {
        order.setPaymentDone();

       int userPoint = order.getUser().getPoint();
       order.getUser().setPoint(userPoint + (int) (order.sumOrderPrice() * 0.07));
    }

    public void checkCanPay(String orderCode, long pgPayPrice) {
        Orders order = findByCode(orderCode).orElse(null);

        if (order == null)
            throw new IllegalArgumentException("존재하지 않는 주문입니다.");

        checkCanPay(order, pgPayPrice);
    }

    public void checkCanPay(Orders order, long pgPayPrice) {
        if (!canPay(order, pgPayPrice))
            throw new IllegalArgumentException("PG결제금액 부족하여 결제할 수 없습니다.");
    }

    public boolean canPay(Orders order, long pgPayPrice) {
        if ( !order.isPayable() ) return false;

        return order.sumOrderPrice() <= pgPayPrice;
    }

    public Optional<Orders> findByCode(String code) {
        long id = Long.parseLong(code.split("_", 2)[1]);

        return findById(id);
    }

    @Transactional
    public void payByTossPayments(Orders order, long pgPayPrice) {
        payDone(order);
    }

    @Transactional
    public void saveOrderPayInfo(Orders order, OrderPayInfo orderPayInfo) {
        order.setName(orderPayInfo.getCustomerName());
        order.setPhone(orderPayInfo.getCustomerMobilePhoneNumber());
        order.setPrice(order.sumOrderPrice());
        order.setAddress1(orderPayInfo.getAddress1());
        order.setAddress2(orderPayInfo.getAddress2());
        order.setZipcode(orderPayInfo.getZipcode());
    }

    public List<Orders> findByUser(User user) {
        return orderRepository.findByUser(user);
    }

}
