package com.trendhub.trendhub.domain.coordi.controller;

import com.trendhub.trendhub.domain.coordi.dto.CoordiDetailDto;
import com.trendhub.trendhub.domain.coordi.dto.CoordiDto;
import com.trendhub.trendhub.domain.coordi.service.CoordiService;
import com.trendhub.trendhub.domain.review.entity.Review;
import com.trendhub.trendhub.domain.review.service.ReviewService;
import com.trendhub.trendhub.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
        Long coordiId = coordiService.postCoordi(file);
        //TODO 업로드후 이동할 화면 얘기해야함
        return "redirect:/coordi/" + coordiId;
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

    @GetMapping("/{coordiId}")
    public String coordiDetail(Model model, @PathVariable("coordiId") Long id,
                               @RequestParam(value = "page", defaultValue = "1") int page){
        CoordiDetailDto coordiDetailDto = coordiService.findById(id);

        Page<Review> reviewPage = reviewService.getReviewList(page, id);

        //댓글
        // List<Review> reviewList = reviewService.findByCoordi(id);

        System.out.println("coordiDto = " + coordiDetailDto);
        System.out.println("reviewPage.getNumber() = " + reviewPage.getNumber());
        // System.out.println("reviewList = " + reviewList);

        model.addAttribute("coordiDetailDto", coordiDetailDto);
        // model.addAttribute("reviewList", reviewList);
        model.addAttribute("paging", reviewPage);

        return "coordiDetail";
    }


}
