package com.trendhub.trendhub.domain.orders.entity;

import com.trendhub.trendhub.domain.cart.entity.Cart;
import com.trendhub.trendhub.domain.orderDetail.entity.OrderDetail;
import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.product.entity.ProductOption;
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

    public void addProduct(Product product, int amount, ProductOption productOption) {
        OrderDetail orderDetail = OrderDetail.builder()
                .order(this)
                .product(product)
                .price(product.getPrice())
                .count(amount)
                .productOption(productOption)
                .build();

        orderDetails.add(orderDetail);
    }

    public void addItem(Cart cart) {
        addProduct(cart.getProduct(), cart.getCount(), cart.getProductOption());
    }

    public String getCode() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return getCreateAt().format(formatter) + UUID.randomUUID().toString() + "_" + getOrdersId();
    }

    public String getOrderName() {
        String name = orderDetails.get(0).getProduct().getName() + " " + orderDetails.get(0).getCount() + "개";

        if (orderDetails.size() > 1) {
            name += " 외 %d건".formatted(orderDetails.size() - 1);
        }

        return name;
    }

    public long sumOrderPrice() {
        return orderDetails.stream()
                .mapToLong(orderDetail -> orderDetail.discountedPrice())
                .sum();
    }

    public boolean isPayable() {
        if (date != null) return false;

        return true;
    }

    public void setPaymentDone() {
        date = LocalDateTime.now();
    }

    public boolean isShippingInfo() {
        if (name != null && phone != null && address1 != null && address2 != null && zipcode != null ) return true;

        return false;
    }

    public String getPayStatus() {
        if (date != null)
            return "결제완료";
//            return "결제완료 (" + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ")";


        return "결제대기";
    }

    public String getOrderCreateAt() {
        return getCreateAt().toString();
    }

    public String getProductImage() {
        String image = orderDetails.get(0).getProduct().getImage();
        System.out.println(image);
        return image;
    }

    public String getOrderProductOption() {
        String orderProductOption = orderDetails.get(0).getProductOption().getColor() + "/" + orderDetails.get(0).getProductOption().getSize();

        return orderProductOption;
    }


}
