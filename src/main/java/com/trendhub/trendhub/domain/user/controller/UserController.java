package com.trendhub.trendhub.domain.user.controller;

import com.trendhub.trendhub.domain.user.dto.FindUserDto;
import com.trendhub.trendhub.domain.user.dto.SignupFormDto;
import com.trendhub.trendhub.domain.user.entity.User;
import com.trendhub.trendhub.domain.user.repository.UserRepository;
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
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@Transactional
@RequiredArgsConstructor
@RequestMapping("/members")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
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
        return "findIdTest";
    }   // 로그인 찾기 페이지 Get

//    @PostMapping("/findLoginId")
//    public String postFindLoginId(Model model, FindUserDto dto,
//                                  @RequestParam String name, @RequestParam String email) {
//        try {
//            dto.setUsername(name);
//            dto.setEmail(email);
//            Optional<User> foundUser = userService.findUserByUsernameAndEmail(dto);
//
//            if (foundUser.isPresent()) {
//                model.addAttribute("findId", foundUser.get().getUserId());
//            } else {
//                model.addAttribute("findId", null); // 아이디가 없으면 null 전달
//            }
//        } catch (Exception e) {
//            model.addAttribute("msg", "오류가 발생되었습니다.");
//            e.printStackTrace();
//        }
//        return "users/login";
//    } // 로그인 찾기 Post 기능

    @ResponseBody
    @PostMapping("/findLoginId")
    public String postFindLoginId(@RequestBody FindUserDto dto) {
        User findUser =userService.findUserByUsernameAndEmail(dto).orElseThrow(
                () -> {
                    throw new NoSuchElementException("Could not find that user.");
                }
        );
        return findUser.getUsername();
    } // 로그인 찾기 Post 기능
}
