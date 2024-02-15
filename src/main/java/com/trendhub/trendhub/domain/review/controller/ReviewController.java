package com.trendhub.trendhub.domain.review.controller;

import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.product.service.ProductService;
import com.trendhub.trendhub.domain.review.dto.ReviewDto;
import com.trendhub.trendhub.domain.review.service.ReviewService;
import com.trendhub.trendhub.domain.user.entity.User;
import com.trendhub.trendhub.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@Slf4j
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ProductService productService;
    private final UserService userService;
    private final ReviewService reviewService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create/{id}")
    public String getReviewWrite(Model model, @PathVariable("id") Long productId, Principal principal,
                                 @ModelAttribute("reviewDto") ReviewDto reviewDto) {
        if (principal.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "로그인 먼저 진행해주세요.");
        }
        Product product = productService.getProduct(productId);
        model.addAttribute("product", product);
        model.addAttribute("productId", productId);
        model.addAttribute("reviewDto", reviewDto);
        /*model.addAttribute("product", productService.getProductsByIds(List.of(productId)).get(0));*/  // 현재 getProductsByIds 함수는 여러 개 가져오는 함수, 단건 조회 할 수 있는 함수가 필요
        return "products/reviewWrite";
    }

    @PostMapping("/create/{id}")
    public String postReviewWrite(Model model, @PathVariable("id") Long productId,
                                  @RequestParam(value = "content") String content, Principal principal,
                                  @Valid @ModelAttribute("reviewDto") ReviewDto reviewDto, BindingResult bindingResult
                                    /*,@RequestPart("file") MultipartFile file*/)
    {
        Product product = productService.getProduct(productId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            model.addAttribute("productId", productId);
            model.addAttribute("reviewDto", reviewDto);
            return "products/reviewWrite";
        }
        try {
            String logInid = principal.getName();
            User user = this.userService.getUser(logInid);
            this.reviewService.createReview(user, product, reviewDto);
        } catch(Exception e) {
            model.addAttribute("product", product);
            model.addAttribute("productId", productId);
            model.addAttribute("reviewDto", reviewDto);
            model.addAttribute("errorMessage", e.getMessage());
            return "products/reviewWrite"; // 에러 발생 시에는 다시 원래의 입력 페이지를 보여줍니다.
        }
        return String.format("redirect:/products/%s", productId);
    }
}