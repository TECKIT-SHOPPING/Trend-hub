package com.trendhub.trendhub.domain.orderDetail.service;

import com.trendhub.trendhub.domain.orderDetail.entity.OrderDetail;
import com.trendhub.trendhub.domain.orderDetail.repository.OrderDetailRepository;
import com.trendhub.trendhub.domain.user.entity.User;
import com.trendhub.trendhub.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;

    public boolean checkBuyProduct(Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() == "anonymousUser") {
            return false;
        } else {
            User user = userRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
            List<OrderDetail> orderDetails = orderDetailRepository.findByOrderAndProduct(user, productId);
            if (!orderDetails.isEmpty()) {
                return !orderDetails.get(0).getOrder().isPayable();
            } else {
                return false;
            }
        }

    }
}
