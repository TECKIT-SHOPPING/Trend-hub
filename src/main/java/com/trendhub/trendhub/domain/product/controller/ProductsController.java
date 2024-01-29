package com.trendhub.trendhub.domain.product.controller;

import com.trendhub.trendhub.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductsController {

    @GetMapping("")
    public String products_list(){

        return "products/products_list";
    }

//    @GetMapping("/{id}")
//    public String products_detail(@PathVariable("id") Long id, Model model, Product product){
//
//        return "products/products_list";
//    }
}
