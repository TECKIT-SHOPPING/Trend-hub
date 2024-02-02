package com.trendhub.trendhub.domain.email.controller;

import com.trendhub.trendhub.domain.email.dto.SignUpEmailReq;
import com.trendhub.trendhub.domain.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/join")
    @ResponseBody
    public boolean sendAuthEmail(@RequestBody SignUpEmailReq signUpEmailReq) throws Exception {
        emailService.sendEmailAuth(signUpEmailReq.getEmail()+"@"+signUpEmailReq.getEmailDomain());
        return true;
    }

    @PostMapping("/join/resend")
    @ResponseBody
    public boolean resendAuthEmail(@RequestBody SignUpEmailReq signUpEmailReq) throws Exception {
        emailService.sendEmailAuth(signUpEmailReq.getEmail()+"@"+signUpEmailReq.getEmailDomain());
        return true;
    }

    @GetMapping("/confirm")
    @ResponseBody
    public boolean confirmAuthEmail(@RequestParam("q") String q) {
        return emailService.verifyAuthCode(q);
    }
}
