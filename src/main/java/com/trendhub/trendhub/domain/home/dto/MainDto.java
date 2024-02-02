package com.trendhub.trendhub.domain.home.dto;

import com.trendhub.trendhub.domain.banner.entity.Banner;
import com.trendhub.trendhub.domain.coordi.dto.CoordiDto;
import com.trendhub.trendhub.domain.product.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class MainDto {

    private List<Banner> banners;
    private List<ProductDto> products;
    private List<CoordiDto> coordis;

    public MainDto(List<Banner> banners, List<ProductDto> products, List<CoordiDto> coordis) {
        this.banners = banners;
        this.products = products;
        this.coordis = coordis;
    }
}
