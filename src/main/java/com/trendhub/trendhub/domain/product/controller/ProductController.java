package com.trendhub.trendhub.domain.product.controller;

import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/product")
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;


    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id){
        Product product = this.productService.getPost(id);
        System.out.println("클릭한 페이지 아이디 : " + product.getProductId());
        model.addAttribute("product", product);
        return "product/productDetail";

    @ResponseBody
    @PostMapping("/api/products/liked")
    public boolean toggleLikeProduct(@RequestBody ProductLikeDto productLikeDto) {
        boolean result = productService.toggleLikeProduct(productLikeDto);
        return result;
    }
}
