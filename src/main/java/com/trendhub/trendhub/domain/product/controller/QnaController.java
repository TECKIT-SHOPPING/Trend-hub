package com.trendhub.trendhub.domain.product.controller;

import com.trendhub.trendhub.domain.product.dto.QnaAnswerDto;
import com.trendhub.trendhub.domain.product.dto.QnaDto;
import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.product.entity.QnA;
import com.trendhub.trendhub.domain.product.entity.QnaAnswer;
import com.trendhub.trendhub.domain.product.service.ProductService;
import com.trendhub.trendhub.domain.product.service.QnaService;
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
import java.util.List;

@Slf4j
@RequestMapping("/qna")
@RequiredArgsConstructor
@Controller
public class QnaController {

    private final QnaService qnaService;
    private final ProductService productService;
    private final UserService userService;

    @GetMapping(value = "/detail/{id}")
    public String qnaDetail(Model model, @PathVariable("id") Long id) {
        QnA qnA = this.qnaService.getQnaDetail(id);
        List<QnaAnswer> qnaAnswer = this.qnaService.getQnaAnswer();
        model.addAttribute("qnaDetail", qnA);
        model.addAttribute("qnaAnswer", qnaAnswer);
        return "products/qnaDetail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public String getInquireWrite(Model model, @PathVariable("id") Long productId, Principal principal, QnaDto qnaDto) {
        if (principal.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "로그인 먼저 진행해주세요.");
        }
        model.addAttribute("productId", productId);
        model.addAttribute("qnaDto", qnaDto);
        model.addAttribute("product", productService.getProductsByIds(List.of(productId)).get(0));  // 현재 getProductsByIds 함수는 여러 개 가져오는 함수, 단건 조회 할 수 있는 함수가 필요
        return "products/popup_inquire_write";
    }

    @PostMapping("/{id}")
    public String postInquireWrite(Model model, @PathVariable("id") Long productId, Principal principal,
                                   @Valid @ModelAttribute("qnaDto") QnaDto qnaDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("productId", productId);
            model.addAttribute("qnaDto", qnaDto);
            return "products/popup_inquire_write";
        }
        try {
            String logInid = principal.getName();
            Product product = this.productService.getProduct(productId);
            User user = this.userService.getUser(logInid);
            this.qnaService.createQna(qnaDto, product, user);
        } catch (Exception e) {
            model.addAttribute("productId", productId);
            model.addAttribute("qnaDto", qnaDto);
            model.addAttribute("errorMessage", e.getMessage());
            return "products/popup_inquire_write"; // 에러 발생 시에는 다시 원래의 입력 페이지를 보여줍니다.
        }
        return "products/completeQnA";
    }

    @PostMapping("/create/{id}")
    public String createQnaAnswer(Model model, @PathVariable("id") Long id,
                                  @RequestParam(value = "content") String content, Principal principal,
                                  @Valid @ModelAttribute("qnaAnswerDto") QnaAnswerDto qnaAnswerDto)
    {
        String logInid = principal.getName();
        QnA qnA = this.qnaService.getQnaDetail(id);
        User user = this.userService.getUser(logInid);
        this.qnaService.createQnaAnswer(qnaAnswerDto, qnA, user);
        return String.format("redirect:/qna/detail/%s", id);
    }
}
