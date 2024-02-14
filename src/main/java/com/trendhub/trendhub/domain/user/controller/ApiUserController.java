package com.trendhub.trendhub.domain.user.controller;

import com.trendhub.trendhub.domain.user.dto.ChangeNicknameDto;
import com.trendhub.trendhub.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class ApiUserController {

    private final UserService userService;

    @GetMapping("/check-nickname")
    public ResponseEntity<String> checkNickname(@RequestParam("nickname") String nickname) {
        System.out.println("nickname = " + nickname);
        userService.checkNickname1(nickname);
        return ResponseEntity.ok("중복된 닉네임이 없습니다");
    }
}
