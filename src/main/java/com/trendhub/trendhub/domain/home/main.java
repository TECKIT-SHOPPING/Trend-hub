package com.trendhub.trendhub.domain.home;

import com.trendhub.trendhub.domain.home.dto.MainDto;
import com.trendhub.trendhub.domain.home.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@RequiredArgsConstructor
public class main {

    private final MainService mainService;

    @GetMapping("/")
        public String main(Model model) {

            MainDto mainDto = mainService.getMainPage();
            model.addAttribute("mainDto", mainDto);
            return "main";
        }

        @GetMapping("/coordi")
        public String community(){
            return "coordi";
        }
    }