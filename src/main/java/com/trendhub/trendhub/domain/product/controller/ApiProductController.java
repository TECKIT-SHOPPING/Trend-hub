package com.trendhub.trendhub.domain.product.controller;

import com.trendhub.trendhub.domain.product.dto.ProductLikeDto;
import com.trendhub.trendhub.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ApiProductController {

    private final ProductService productService;

    @ResponseBody
    @PostMapping("/liked")
    public boolean toggleLikeProduct(@RequestBody ProductLikeDto productLikeDto) {
        boolean result = productService.toggleLikeProduct(productLikeDto);
        return result;
    }
}
