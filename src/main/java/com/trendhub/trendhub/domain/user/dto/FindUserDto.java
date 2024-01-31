package com.trendhub.trendhub.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindUserDto {
    @NotBlank(message = "이름을 입력해주세요.")
    private String username;

    private String email;
}
