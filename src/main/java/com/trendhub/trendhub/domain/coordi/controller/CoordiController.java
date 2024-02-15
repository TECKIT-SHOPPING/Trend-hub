package com.trendhub.trendhub.domain.coordi.controller;

import com.trendhub.trendhub.domain.coordi.dto.CoordiDetailDto;
import com.trendhub.trendhub.domain.coordi.dto.CoordiDto;
import com.trendhub.trendhub.domain.coordi.entity.Coordi;
import com.trendhub.trendhub.domain.coordi.service.CoordiService;
import com.trendhub.trendhub.domain.review.dto.CoordiReviewDto;
import com.trendhub.trendhub.domain.review.entity.Review;
import com.trendhub.trendhub.domain.review.service.ReviewService;
import com.trendhub.trendhub.domain.user.entity.User;
import com.trendhub.trendhub.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RequiredArgsConstructor
@RequestMapping("/coordi")
@Controller
public class CoordiController {

    private final CoordiService coordiService;
    private final UserService userService;
    private final ReviewService reviewService;

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
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               @Valid @ModelAttribute("coordiReviewDto") CoordiReviewDto coordiReviewDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() != "anonymousUser") {
            User user = null;
            String loginId = authentication.getName();
            user = userService.getUser(loginId);
            model.addAttribute("userId", user.getUserId());
        } else {
            model.addAttribute("userId", null);
        }

        CoordiDetailDto coordiDetailDto = coordiService.findById(id);

        Page<Review> reviewPage = reviewService.getCoordiReviewList(page-1, id);
     

        model.addAttribute("coordiDetailDto", coordiDetailDto);
        model.addAttribute("paging", reviewPage);
        model.addAttribute("CoordiReviewDto", coordiReviewDto);


        return "coordiDetail";
    }

    @PostMapping("/{coordiId}")
    public String coordiReviewWrite(Model model, @PathVariable("coordiId") Long id,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "content") String content, Principal principal,
                                    @Valid @ModelAttribute("coordiReviewDto") CoordiReviewDto coordiReviewDto, BindingResult bindingResult)
    {
        Coordi coordi = coordiService.getCoordi(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("coordi", coordi);
            model.addAttribute("coordiReviewDto", coordiReviewDto);
            return "redirect:/coordi/"+ coordi.getCoordiId();
        }
        try {
            String logInid = principal.getName();
            User user = this.userService.getUser(logInid);
            this.reviewService.createCoordiReview(user, coordi, coordiReviewDto);
        } catch(Exception e) {
            model.addAttribute("coordi", coordi);
            model.addAttribute("coordiReviewDto", coordiReviewDto);
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/coordi/" + coordi.getCoordiId();
    }

}
