package com.trendhub.trendhub.domain.product.controller;

import com.trendhub.trendhub.domain.orderDetail.service.OrderDetailService;
import com.trendhub.trendhub.domain.product.dto.ProductDto;
import com.trendhub.trendhub.domain.product.dto.QnaDto;
import com.trendhub.trendhub.domain.product.entity.*;
import com.trendhub.trendhub.domain.product.service.MainCategoryService;
import com.trendhub.trendhub.domain.product.service.ProductService;
import com.trendhub.trendhub.domain.product.service.QnaService;
import com.trendhub.trendhub.domain.product.service.SubCategoryService;
import com.trendhub.trendhub.domain.review.entity.Review;
import com.trendhub.trendhub.domain.review.service.ReviewService;
import com.trendhub.trendhub.domain.user.entity.User;
import com.trendhub.trendhub.domain.user.service.UserService;
import com.trendhub.trendhub.global.service.PageCustom;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Slf4j
@RequestMapping("/products")
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final MainCategoryService mainCategoryService;
    private final SubCategoryService subCategoryService;
    private final QnaService qnaService;
    private final ReviewService reviewService;
    private final OrderDetailService orderDetailService;

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable("id") Long id,
                         @RequestParam(value = "qnaPage", defaultValue = "0") int qnaPage
                         , @RequestParam(value = "reviewPage", defaultValue = "0") int reviewPage,
                         Principal principal) {
        Product product = this.productService.getProduct(id);
        Long productId = product.getProductId();
        ProductDto productDto = productService.findByCheckLike(productId);
        Page<QnA> qnAList = this.qnaService.getQnAList(qnaPage, productId);
        Page<Review> reviewList = this.reviewService.getReviewList(reviewPage, productId);
        boolean checkPayable = orderDetailService.checkBuyProduct(productId);

        List<ProductOption> productOption = this.productService.findProductOption(product);


        String logInid;
        if (principal != null) {
            logInid = principal.getName();
        } else {
            logInid = null;
        }
        User user = this.userService.getUser(logInid);
        model.addAttribute("user", user);
        model.addAttribute("product", product);
        model.addAttribute("qnaPaging", qnAList);

        model.addAttribute("productOption", productOption);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("checkPayable", checkPayable);
        model.addAttribute("productDto", productDto);

        return "products/productDetail";
    }

    @GetMapping("/{mainCategory}/{subCategory}")
    public String categoryProductList(
            @PathVariable("mainCategory") Long mainCategory,
            @PathVariable("subCategory") Long subCategory,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "sort", required = false, defaultValue = "popular") String sort,
            Model model
    ) {
        //  상의(1)  ->  티셔츠(1), 긴소매 티셔츠(2), 맨투맨(3)
        //  하의(2)  ->  반바지(4), 긴바지(5)
        //  아우터(3) ->  패딩(6), 코트(7), 기타(8)
        //  신발(4)  ->  스니커즈(9), 구두(10), 슬리퍼(11)
        //  원피스(5) -> 미니 원피스(12), 미디 원피스(13), 맥스 원피스(14)
        //  가방(6)  ->  캐쥬얼(15), 스포츠(16)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() != "anonymousUser") {
            User user = userService.getUser(authentication.getName());
            model.addAttribute("user", user);
        }
        Page<ProductDto> pageProducts = productService.categoryProductList(mainCategory, subCategory, page, sort);
        PageCustom<ProductDto> products = (PageCustom<ProductDto>) new PageCustom<>(pageProducts.getContent(), pageProducts.getPageable(), pageProducts.getTotalElements());
        MainCategory findMainCategory = mainCategoryService.findById(mainCategory);
        SubCategory findSubCategory = subCategoryService.findById(subCategory);
        model.addAttribute("sort", sort);
        model.addAttribute("mainCategory", findMainCategory);
        model.addAttribute("subCategory", findSubCategory);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        return "products/productList";

    }
// 수정 2/10

    @PostMapping("/qna/{id}")
    public String postInquireWrite(Model model, @PathVariable("id") Long productId, Principal principal,
                                   @Valid @ModelAttribute("qnaDto") QnaDto qnaDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("productId", productId);
            model.addAttribute("qnaDto", qnaDto);
            return "products/popup_inquire_write";
        }
        try {
            Product product = this.productService.getProduct(productId);
            String logInid = principal.getName();
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


    @GetMapping("/search")
    public String searchProduct(
            @RequestParam("q") String q,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "sort", required = false, defaultValue = "popular") String sort,
            Model model
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() != "anonymousUser") {
            User user = userService.getUser(authentication.getName());
            model.addAttribute("user", user);
        }
        Page<ProductDto> pageProducts = productService.searchProductList(q, page, sort);
        PageCustom<ProductDto> products = (PageCustom<ProductDto>) new PageCustom<>(pageProducts.getContent(), pageProducts.getPageable(), pageProducts.getTotalElements());
        model.addAttribute("q", q.replaceAll("\\s+", " ").trim());
        model.addAttribute("sort", sort);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);

        return "products/productSearch";
    }
}