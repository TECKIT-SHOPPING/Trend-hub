package com.trendhub.trendhub.domain.product.controller;

import com.trendhub.trendhub.domain.product.dto.ProductLikeDto;
import com.trendhub.trendhub.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @ResponseBody
    @PostMapping("/api/products/liked")
    public boolean toggleLikeProduct(@RequestBody ProductLikeDto productLikeDto) {
        boolean result = productService.toggleLikeProduct(productLikeDto);
        return result;
    }
}
