package com.trendhub.trendhub.domain.cart.controller;


import com.trendhub.trendhub.domain.cart.entity.Cart;
import com.trendhub.trendhub.domain.cart.service.CartService;
import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.product.service.ProductService;
import com.trendhub.trendhub.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final Rq rq;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("")
    public String showCartList(){
        List<Cart> cartList = cartService.getCartList(rq.getUserInfo());

        rq.setAttribute("cartList", cartList);

        return "products/cart";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}")
    public String addCartProduct(@PathVariable("id") Long productId,
                                 RedirectAttributes redirectAttributes){
        try {
            Product product = productService.getProduct(productId);
            cartService.addCartProduct(rq.getUserInfo(), product);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("cartErrorMessage", e.getMessage());
            return "redirect:/products/" + productId;
        }

        return "redirect:/cart";




//        Product product = productService.getProduct(productId);
//        cartService.addCartProduct(rq.getUserInfo(), product);

//        return "redirect:/cart";
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public String removeCartProduct(@PathVariable("id") Long productId){
        Product product = productService.getProduct(productId);
        cartService.removeCartProduct(rq.getUserInfo(), product);

        return "redirect:/cart";
    }
}
