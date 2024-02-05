package com.trendhub.trendhub.domain.product.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/product")
@RequiredArgsConstructor
@Controller
public class PostController {

    @GetMapping("/detail")
    public String detail(){
        return "product/productDetail";
    }

}
