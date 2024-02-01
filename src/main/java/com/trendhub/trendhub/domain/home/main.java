package com.trendhub.trendhub.domain.home;

import com.trendhub.trendhub.domain.home.dto.MainDto;
import com.trendhub.trendhub.domain.home.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class main {

<<<<<<< HEAD
    private final MainService mainService;

    @GetMapping("/")
    public String main(Model model) {

        MainDto mainDto = mainService.getMainPage();
        model.addAttribute("mainDto", mainDto);
=======
    @GetMapping("/")
    public String main(){
>>>>>>> 6aeb89e (feat/마이페이지 화면구현 충돌해결)
        return "main";
    }

    @GetMapping("/coordi")
    public String community(){
        return "coordi";
    }

}
