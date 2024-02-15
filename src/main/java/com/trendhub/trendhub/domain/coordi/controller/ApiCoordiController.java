package com.trendhub.trendhub.domain.coordi.controller;

import com.trendhub.trendhub.domain.coordi.dto.CoordiLikeDto;
import com.trendhub.trendhub.domain.coordi.service.CoordiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{coordiId}")
    public int deleteCoordi(@PathVariable("coordiId") Long coordiId) {
        int response =  coordiService.deleteCoordi(coordiId);
        return response;
    }
}
