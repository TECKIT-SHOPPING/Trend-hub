package com.trendhub.trendhub.domain.banner.controller;

import com.trendhub.trendhub.domain.banner.entity.Banner;
import com.trendhub.trendhub.domain.banner.service.BannerService;
import com.trendhub.trendhub.domain.product.dto.ProductDto;
import com.trendhub.trendhub.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/banners")
public class BannerController {
    private final BannerService bannerService;
    private final ProductService productService;

    // 배너
    @GetMapping("{id}")
    public String banner(Model model, @PathVariable("id") Long id) {
        Banner banner = bannerService.getBanner(id);
        List<ProductDto> product = null;

        if (banner.getBannerId() == 1) {
            product = productService.findTop20ByOrderByCreateMonthDesc();
        } else if (banner.getBannerId() == 2) {
            product = productService.totalLikeTop20();
        } else if (banner.getBannerId() == 3) {
            product = productService.findByFWsale();
        }


        model.addAttribute("banner", banner);
        model.addAttribute("product", product);
        return "banner";
    }

}
