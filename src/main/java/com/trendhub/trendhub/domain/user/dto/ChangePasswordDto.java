package com.trendhub.trendhub.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDto {
    @NotBlank(message = "회원님의 비밀번호를 적어주세요.")
    @Pattern(
            regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "영문자(A-Z, a-z), 숫자, 특수문자로 구성된 8~16자입니다."
    )
    private String originPassword;

    @NotBlank(message = "회원님의 비밀번호를 적어주세요.")
    @Pattern(
            regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "영문자(A-Z, a-z), 숫자, 특수문자로 구성된 8~16자입니다."
    )
    private String newPassword;

    @NotBlank(message = "회원님의 비밀번호를 적어주세요.")
    @Pattern(
            regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "영문자(A-Z, a-z), 숫자, 특수문자로 구성된 8~16자입니다."
    )
    private String checkNewPassword;
}
