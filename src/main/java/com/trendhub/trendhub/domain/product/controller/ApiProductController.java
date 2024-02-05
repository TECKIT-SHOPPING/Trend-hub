package com.trendhub.trendhub.domain.product.controller;

import com.trendhub.trendhub.domain.product.dto.ProductDto;
import com.trendhub.trendhub.domain.product.dto.ProductLikeDto;
import com.trendhub.trendhub.domain.product.dto.RecentlyProductReq;
import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ApiProductController {

    private final ProductService productService;

    @PostMapping("/liked")
    public boolean toggleLikeProduct(@RequestBody ProductLikeDto productLikeDto) {
        boolean result = productService.toggleLikeProduct(productLikeDto);
        return result;
    }

    @PostMapping("/recently")
    public List<ProductDto> getRecentlyViewedProducts(@RequestBody RecentlyProductReq recentlyProductReq) {
        System.out.println("recentlyProductReq.toString() = " + recentlyProductReq.toString());
        List<Long> uniqueProductIds = recentlyProductReq.getProductIdList().stream()
                .distinct()
                .collect(Collectors.toList());
        List<ProductDto> productsByIds = productService.getProductsByIds(uniqueProductIds);
        System.out.println("productsByIds.toString() = " + productsByIds.toString());
        return productsByIds;
    }
}
