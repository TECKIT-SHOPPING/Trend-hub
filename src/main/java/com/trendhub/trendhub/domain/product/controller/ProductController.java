package com.trendhub.trendhub.domain.product.controller;

import com.trendhub.trendhub.domain.product.dto.QnaDto;
import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.product.service.ProductService;
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

@Slf4j
@RequestMapping("/products")
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;
    private final UserService userService;


    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        Product product = this.productService.getProduct(id);
        model.addAttribute("product", product);
        return "products/productDetail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/qna/{id}")
    public String getInquireWrite(Model model, @PathVariable("id") Long productId, Principal principal, QnaDto qnaDto) {;
        if (principal.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "로그인 먼저 진행해주세요.");
        }
        /*Product product = this.productService.getProduct(productId);*/
        model.addAttribute("productId", productId);
        model.addAttribute("qnaDto", qnaDto);
        return "products/popup_inquire_write";
    }

    @PostMapping("/qna/{id}")
    public String postInquireWrite(Model model, @PathVariable("id") Long productId, Principal principal,
                                   @Valid @ModelAttribute("qnaDto") QnaDto qnaDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("{}", bindingResult.toString());
            System.out.println("product = " + productId);
            model.addAttribute("productId", productId);
            model.addAttribute("qnaDto", qnaDto);
            return "products/popup_inquire_write";
        }
        try {
            String logInid = principal.getName();
            Product product = this.productService.getProduct(productId);
            User user = this.userService.getUser(logInid);
            this.productService.createQna(qnaDto, product, user);
        } catch (Exception e) {
            model.addAttribute("productId", productId);
            model.addAttribute("qnaDto", qnaDto);
            model.addAttribute("errorMessage", e.getMessage());
            return "products/popup_inquire_write"; // 에러 발생 시에는 다시 원래의 입력 페이지를 보여줍니다.
        }
        return "products/completeQnA";
    }

}