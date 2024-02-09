package com.trendhub.trendhub.domain.coordi.controller;

import com.trendhub.trendhub.domain.coordi.dto.CoordiDto;
import com.trendhub.trendhub.domain.coordi.dto.CoordiLikeDto;
import com.trendhub.trendhub.domain.coordi.entity.Coordi;
import com.trendhub.trendhub.domain.coordi.service.CoordiService;
import com.trendhub.trendhub.domain.product.dto.ProductDto;
import com.trendhub.trendhub.domain.product.dto.ProductLikeDto;
import com.trendhub.trendhub.domain.user.entity.User;
import com.trendhub.trendhub.domain.user.repository.UserRepository;
import com.trendhub.trendhub.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/coordi")
@Controller
public class CoordiController {

    private final CoordiService coordiService;
    private final UserService userService;

    /**
     * 코디 작성 화면 조회
     */
    @GetMapping("/write")
    public String coordiWrite() {
        return "coordiWrite";
    }

    /**
     * 코디 작성 저장
     */
    @PostMapping("/write")
    public String postCoordi(@RequestPart("file") MultipartFile file) throws Exception {
        coordiService.postCoordi(file);
        //TODO 업로드후 이동할 화면 얘기해야함
        return "redirect:/";
    }

    @GetMapping("")
    public String getCoordiPage(Model model,
                                Pageable pageable,
                                @RequestParam(value="page", defaultValue = "1") int page) {
        Page<CoordiDto> paging = coordiService.getCoordiPage(pageable, page);

        model.addAttribute("currentPage", page);
        model.addAttribute("paging", paging);


        return "coordi";
    }


}
