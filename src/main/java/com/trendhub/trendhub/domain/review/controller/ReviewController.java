package com.trendhub.trendhub.domain.review.controller;

import com.trendhub.trendhub.domain.product.dto.QnaDto;
import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@Slf4j
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ProductService productService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public String getReviewWrite(Model model, @PathVariable("id") Long productId, Principal principal, QnaDto qnaDto) {
        if (principal.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "로그인 먼저 진행해주세요.");
        }
        Product product = productService.getProduct(productId);
        model.addAttribute("product", product);
        model.addAttribute("productId", productId);
        model.addAttribute("qnaDto", qnaDto);
        /*model.addAttribute("product", productService.getProductsByIds(List.of(productId)).get(0));*/  // 현재 getProductsByIds 함수는 여러 개 가져오는 함수, 단건 조회 할 수 있는 함수가 필요
        return "products/popup_inquire_write";
    }
}
