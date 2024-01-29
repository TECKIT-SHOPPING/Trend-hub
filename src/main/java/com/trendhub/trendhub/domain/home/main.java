package com.trendhub.trendhub.domain.home;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class main {

    @GetMapping("/home")
    public String main(){
        return "main";
    }

    @GetMapping("/community")
    public String community(){
        return "community";
    }

    @GetMapping("/membership")
    public String membership(){
        return "membership";
    }
}
