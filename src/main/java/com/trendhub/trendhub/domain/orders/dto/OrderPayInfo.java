package com.trendhub.trendhub.domain.orders.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPayInfo {
    private String customerName;
    private String customerMobilePhoneNumber;
    private String zipcode;
    private String address1;
    private String address2;
}
