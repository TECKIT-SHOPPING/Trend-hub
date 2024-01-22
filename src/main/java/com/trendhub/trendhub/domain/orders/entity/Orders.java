package com.trendhub.trendhub.domain.orders.entity;

import com.trendhub.trendhub.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordersId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Date date;
    private String status;
    private String name;
    private String address1; // 주소
    private String address2; // 상세주소
    private String zipcode;
    private String request; // 요청사항
    private String phone;
    private Long price;
}
