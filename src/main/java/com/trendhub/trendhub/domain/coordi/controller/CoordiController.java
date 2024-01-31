package com.trendhub.trendhub.domain.coordi.controller;

import com.trendhub.trendhub.domain.coordi.service.CoordiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/coordi")
@Controller
public class CoordiController {

    private final CoordiService coordiService;

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
}
