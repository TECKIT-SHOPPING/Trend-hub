package com.trendhub.trendhub.domain.user.controller;

import com.trendhub.trendhub.domain.user.dto.*;
import com.trendhub.trendhub.domain.user.repository.UserRepository;
import com.trendhub.trendhub.domain.user.dto.FindUserDto;
import com.trendhub.trendhub.domain.user.dto.SignupFormDto;
import com.trendhub.trendhub.domain.user.entity.User;
import com.trendhub.trendhub.domain.user.repository.UserRepository;
import com.trendhub.trendhub.domain.user.service.UserService;
import com.trendhub.trendhub.global.rq.Rq;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.NoSuchElementException;



@Slf4j
@Controller
@Transactional
@RequiredArgsConstructor
@RequestMapping("/members")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Rq rq;

    @GetMapping("/join")
    public String getSignup(@ModelAttribute("signupFormDto") SignupFormDto signupFormDto, Model model) {
        model.addAttribute("signupFormDto", signupFormDto);
        return "users/joinMemberForm";
    }

    @PostMapping("/join")
    public String postSignup(@Valid @ModelAttribute("signupFormDto") SignupFormDto signupFormDto, BindingResult bindingResult, Model model) {
        System.out.println("memberFormDto.toString() = " + signupFormDto.toString());
        if (bindingResult.hasErrors()) {
            return "users/joinMemberForm";
        }

        try {
            userService.saveMember(signupFormDto);
        } catch (Exception e) {
            System.out.println("e = " + e);
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("signupFormDto", signupFormDto);  // Add this line to retain form data
            return "users/joinMemberForm";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "users/login";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {

        model.addAttribute("loginErrorMessage", "아이디 또는 비밀번호를 입력해주세요");
        return "users/login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }

    @GetMapping("/findLoginId")
    public String getFindLoginId() {
        return "users/findID";
    }   // 로그인 찾기 페이지 Get

    @GetMapping("/findLoginId/error")
    public String FindLoginIdError(Model model) {

        model.addAttribute("findIdErrorMessage", "이름 또는 이메일을 입력해주세요");
        return "users/findID";
    }

    @ResponseBody
    @PostMapping("/findLoginId")
    public ResponseEntity<Object> postFindLoginId(@RequestBody FindUserDto dto) throws Exception {
        log.info("name={}, email={}", dto.getUsername(), dto.getEmail());

        userService.findId(dto.getUsername(), dto.getEmail());

        /*User findUser =userService.findUserByUsernameAndEmail(dto).orElseThrow(
                () -> {
                    throw new NoSuchElementException("Could not find that user.");
                }
        );*/
        return ResponseEntity.ok().build();
                /*findUser.getUsername()*/
    } // 로그인 찾기 Post 기능


    @GetMapping("/findLoginPw")
    public String getFindLoginPw() {
        return "users/findPW";
    }   // 로그인 찾기 페이지 Get

    @GetMapping("/findLoginPw/error")
    public String FindLoginPwError(Model model) {

        model.addAttribute("findPwErrorMessage", "이름, 이메일, 아이디를 입력해주세요");
        return "users/findPW";
    }

    @ResponseBody
    @PostMapping("/findLoginPw")
    public ResponseEntity<Object> postFindLoginPw(@RequestBody FindUserDto dto) throws Exception {
        log.info("loginId={}, email={}", dto.getLoginId(), dto.getEmail());

        userService.findPw(dto.getLoginId(), dto.getEmail());
        /*User findUser =userService.findUserByUsernameAndEmail(dto).orElseThrow(
                () -> {
                    throw new NoSuchElementException("Could not find that user.");
                }
        );*/
        return ResponseEntity.ok().build();
        /*findUser.getUsername()*/
    } // 로그인 찾기 Post 기능
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/info")
    public String userInfo () {
        return "users/userInfo";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public String userInfoModify () {
        return "users/userInfoModify";
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/change-password")
    public String changePassword (
            @Valid ChangePasswordDto changePasswordDto
    ) {
        userService.changePassword(rq.getUserInfo(), changePasswordDto);

        return "users/userInfoModify";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/check-nickname")
    public String checkNickname (
            ChangeNicknameDto changeNicknameDto
    ) {
        userService.checkNickname(changeNicknameDto);

        return "users/userInfoModify";
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/change-nickname")
    public String changeNickname (
            ChangeNicknameDto changeNicknameDto
    ) {
        userService.changeNickname(rq.getUserInfo(), changeNicknameDto);

        return "users/userInfoModify";
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/change-profile")
    public String changeProfile (
            @RequestPart MultipartFile profile
    ) {
        userService.changeProfile(rq.getUserInfo(), profile);

        return "users/userInfoModify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/address")
    public String address (AddressDto addressDto) {
        userService.saveAddress(rq.getUserInfo(), addressDto);

        return "users/userInfoModify";
    }
    // 이메일 및 이름 가져와서 맞는지 확인하기

    @GetMapping("/myPage/1")
    public String mypage_exp(Principal principal, Model model) {
        String logInid = principal.getName();
        User user = this.userService.getUser(logInid);
        model.addAttribute("user", user);
        return "users/myPage_1";
    }

    @GetMapping("/myPage/2")
    public String mypage_looking(Principal principal, Model model) {
        String logInid = principal.getName();
        User user = this.userService.getUser(logInid);
        model.addAttribute("user", user);
        return "users/myPage_2";
    }

    @GetMapping("/myPage/3")
    public String mypage_like(Principal principal, Model model) {
        String logInid = principal.getName();
        User user = this.userService.getUser(logInid);
        model.addAttribute("user", user);
        return "users/myPage_3";
    }

    @GetMapping("/myPage/4")
    public String mypage_riview(Principal principal, Model model) {
        String logInid = principal.getName();
        User user = this.userService.getUser(logInid);
        model.addAttribute("user", user);
        return "users/myPage_4";
    }
}
