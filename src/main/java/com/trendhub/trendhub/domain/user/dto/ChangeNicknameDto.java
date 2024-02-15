package com.trendhub.trendhub.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeNicknameDto {
    @Pattern(
            regexp = "^[a-zA-Z0-9_]{2,20}$",
            message = "닉네임은 영대소문자, 숫자, 특수문자 _ 만 가능하고 최소 2자 ~ 20자여야합니다."
    )
    private String nickname;
}
