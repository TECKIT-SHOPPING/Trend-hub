package com.trendhub.trendhub.domain.product.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecentlyProductReq {

    private List<Long> productIdList;

}
