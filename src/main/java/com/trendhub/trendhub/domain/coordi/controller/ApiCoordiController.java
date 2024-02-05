package com.trendhub.trendhub.domain.coordi.controller;

import com.trendhub.trendhub.domain.coordi.dto.CoordiLikeDto;
import com.trendhub.trendhub.domain.coordi.service.CoordiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coordi")
@RequiredArgsConstructor
public class ApiCoordiController {

    private final CoordiService coordiService;

    @PostMapping("/liked")
    public boolean toggleLikeCoordi(@RequestBody CoordiLikeDto coordiLikeDto) {
        boolean result = coordiService.toggleLikeCoordi(coordiLikeDto);
        return result;
    }
}
