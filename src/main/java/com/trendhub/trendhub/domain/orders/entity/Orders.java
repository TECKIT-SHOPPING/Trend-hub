package com.trendhub.trendhub.domain.orders.entity;

import com.trendhub.trendhub.domain.orderDetail.entity.OrderDetail;
import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.user.entity.User;
import com.trendhub.trendhub.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Orders extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordersId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<OrderDetail> orderDetails = new ArrayList<>();

    private LocalDateTime date;
    private String status;
    private String name;
    private String address1; // 주소
    private String address2; // 상세주소
    private String zipcode;
    private String request; // 요청사항
    private String phone;
    private Long price;

    public void addProduct(Product product) {

        OrderDetail orderDetail = OrderDetail.builder()
                .order(this)
                .product(product)
                .price(product.getPrice())
                .count(1)
                .build();

        orderDetails.add(orderDetail);
    }

    public String getCode() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return getCreateAt().format(formatter) + UUID.randomUUID().toString() + "_" + getOrdersId();
    }

    public String getName() {
        String name = orderDetails.get(0).getProduct().getName();

        if (orderDetails.size() > 1) {
            name += " 외 %d건".formatted(orderDetails.size() - 1);
        }

        return name;
    }

    public long sumOrderPrice() {
        return orderDetails.stream()
                .mapToLong(OrderDetail::getPrice)
                .sum();
    }

    public boolean isPayable() {
        if (date != null) return false;

        return true;
    }

    public void setPaymentDone() {
        date = LocalDateTime.now();

    }
}
