package com.trendhub.trendhub.domain.email.service;

import com.trendhub.trendhub.domain.email.dto.VerifyEmailReq;
import com.trendhub.trendhub.domain.email.entity.EmailAuth;
import com.trendhub.trendhub.domain.email.repository.EmailAuthRepository;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailAuthRepository emailAuthRepository;
    private String authCode;

    public void sendEmailAuth(String email) throws Exception {
        Optional<EmailAuth> _findEmailAuth = emailAuthRepository.findByEmail(email);
        if (_findEmailAuth.isPresent()) {
            //이메일이 존재한다면 제거
            EmailAuth findEmailAuth = _findEmailAuth.get();
            emailAuthRepository.delete(findEmailAuth);
        }
        try {
            MimeMessage message = createEmailContent(email);
            javaMailSender.send(message);
            EmailAuth emailAuth = EmailAuth.createEmailAuth(email, authCode);
            emailAuthRepository.save(emailAuth);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendEmailId(String email, String loginId) throws Exception {
        try {
            MimeMessage message = createEmailContentId(email, loginId);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendEmailPw(String email, String tempPw) throws Exception {
        try {
            MimeMessage message = createEmailContentPw(email, tempPw);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public boolean verifyEmailCode(VerifyEmailReq verifyEmailReq) {
        EmailAuth emailAuth = emailAuthRepository.findByEmailAndAuthCode(verifyEmailReq.getEmail(), verifyEmailReq.getAuthCode())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 인증번호입니다."));
        LocalDateTime createAt = emailAuth.getCreateAt();
        LocalDateTime currentAt = LocalDateTime.now();

        Duration duration = Duration.between(createAt, currentAt);
        long minutesDifference = duration.toMinutes();
        if (minutesDifference > 10) {
            throw new IllegalArgumentException("유효시간 10분을 지났습니다.");
        }
        emailAuth.verifyEmail();
        return true;
    }

    public boolean verifyAuthCode(String authCode) {
        EmailAuth emailAuth = emailAuthRepository.findByAuthCode(authCode)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 인증번호입니다."));
        LocalDateTime createAt = emailAuth.getCreateAt();
        LocalDateTime currentAt = LocalDateTime.now();

        Duration duration = Duration.between(createAt, currentAt);
        long minutesDifference = duration.toMinutes();
        if (minutesDifference > 10) {
            return false; //유효시간 10분 지남
        }
        emailAuth.verifyEmail();
        return true;
    }

    private MimeMessage createEmailContent(String email) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, email);
        message.setSubject("TrendHub 회원가입 이메일 인증");

        authCode = createAuthCode();

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요 TrendHub입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<p>감사합니다!<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += authCode + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("trendhub@gmail.com", "TrendHub"));//보내는 사람

        return message;
    }

    private MimeMessage createEmailContentId(String email, String loginId) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, email);
        message.setSubject("TrendHub 아이디 찾기 확인");

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요 TrendHub입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>아래 아이디를 확인해주세요<p>";
        msgg += "<p>아래 코드를 로그인 창으로 돌아가 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<p>감사합니다!<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>고객님의 아이디입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "Id : <strong>";
        msgg += loginId + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("trendhub@gmail.com", "TrendHub"));//보내는 사람

        return message;
    }

    private MimeMessage createEmailContentPw(String email, String tempPw) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, email);
        message.setSubject("TrendHub 아이디 찾기 확인");
        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요 TrendHub입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>임시 비밀번호 입력 후 내 정보에서 비밀번호를 변경해주세요<p>";
        msgg += "<br>";
        msgg += "<p>감사합니다!<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>임시 비밀번호 입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "Id : <strong>";
        msgg += tempPw + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("trendhub@gmail.com", "TrendHub"));//보내는 사람

        return message;
    }



    private String createAuthCode() {
        Random random = new Random();
        String authCode = "";

        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(25) + 65;
            authCode += (char) index;
        }

        int numIndex = random.nextInt(9000) + 1000;
        authCode += numIndex;

        return authCode;
    }
}
