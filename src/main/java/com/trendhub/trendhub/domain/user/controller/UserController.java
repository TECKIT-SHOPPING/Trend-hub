package com.trendhub.trendhub.domain.user.controller;

import com.trendhub.trendhub.domain.user.dto.SignupFormDto;
import com.trendhub.trendhub.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Transactional
@RequiredArgsConstructor
@RequestMapping("/members")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

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
        return "login";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {

        model.addAttribute("loginErrorMessage", "아이디 또는 비밀번호를 입력해주세요");
        return "loginComplete";
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
}
