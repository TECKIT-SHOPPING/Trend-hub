package com.trendhub.trendhub.domain.user.dto;

import com.trendhub.trendhub.domain.user.entity.SocialProvider;
import com.trendhub.trendhub.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignupFormDto {
    @NotBlank(message = "이름을 입력해주세요.")
    private String username;

    @NotEmpty(message = "회원님의 로그인Id를 적어주세요.")
    @Pattern(
            regexp = "^(?=.*[a-z])[a-z0-9]{6,20}$",
            message = "id는 소문자 하나이상있어야하고, 6자~20자여야합니다."
    )
    private String loginId;

    @NotBlank(message = "회원님의 비밀번호를 적어주세요.")
    @Pattern(
            regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "영문자(A-Z, a-z), 숫자, 특수문자로 구성된 8~16자입니다."
    )
    private String password;
    private String checkPassword;

    private boolean agreeAll;
    private boolean agreeInfo;
    private boolean agreeAge;
    private boolean agreeEmail;

    private String email;
    private String emailDomain;

    private int year;
    private int month;
    private int day;

    public User toEntity(SignupFormDto signupFormDto, String randomNickname, PasswordEncoder passwordEncoder) {
        return User.builder()
                .loginId(signupFormDto.getLoginId())
                .password(passwordEncoder.encode(signupFormDto.getPassword()))
                .username(signupFormDto.getUsername())
                .nickname(randomNickname)
                .email(signupFormDto.getEmail())
                .birth(Date.from(LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .profile("https://ifh.cc/g/NLF78g.png")
                .provider(SocialProvider.APP)
                .providerId("-1")
                .agreeInfo(LocalDateTime.now())
                .agreeAge(LocalDateTime.now())
                .agreeEmail(signupFormDto.isAgreeEmail() ? null : LocalDateTime.now())
                .emailAuthChecked(true)
                .level(1)
                .point(0)
                .status("ACTIVE")
                .build();
    }
}
