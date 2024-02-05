package com.trendhub.trendhub.domain.home.service;

import com.trendhub.trendhub.domain.banner.entity.Banner;
import com.trendhub.trendhub.domain.banner.service.BannerService;
import com.trendhub.trendhub.domain.coordi.dto.CoordiDto;
import com.trendhub.trendhub.domain.coordi.service.CoordiService;
import com.trendhub.trendhub.domain.home.dto.MainDto;
import com.trendhub.trendhub.domain.product.dto.ProductDto;
import com.trendhub.trendhub.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MainService {

    private final BannerService bannerService;
    private final ProductService productService;
    private final CoordiService coordiService;


    public MainDto getMainPage() {
        List<Banner> banners = bannerService.findAll();
        List<ProductDto> products = productService.findTop10ViewCountDesc();
        List<CoordiDto> coordis = coordiService.findTop5ViewCountDesc();
        MainDto mainDto = new MainDto(banners, products, coordis);
        return mainDto;
    }
}
