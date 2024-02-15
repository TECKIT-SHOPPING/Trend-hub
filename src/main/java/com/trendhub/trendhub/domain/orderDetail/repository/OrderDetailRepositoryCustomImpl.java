package com.trendhub.trendhub.domain.orderDetail.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trendhub.trendhub.domain.orderDetail.entity.OrderDetail;
import com.trendhub.trendhub.domain.orderDetail.entity.QOrderDetail;
import com.trendhub.trendhub.domain.orders.entity.QOrders;
import com.trendhub.trendhub.domain.product.entity.QProduct;
import com.trendhub.trendhub.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderDetailRepositoryCustomImpl implements OrderDetailRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<OrderDetail> findByOrderAndProduct(User user, Long productId) {
        List<OrderDetail> result = jpaQueryFactory
                .select(QOrderDetail.orderDetail)
                .from(QOrderDetail.orderDetail)
                .join(QOrderDetail.orderDetail.order, QOrders.orders)
                .join(QOrderDetail.orderDetail.product, QProduct.product)
                .where(QProduct.product.productId.eq(productId).and(QOrders.orders.date.isNotNull()))
                .fetch();

        return result;
    }
}
