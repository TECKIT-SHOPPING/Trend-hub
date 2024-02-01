package com.trendhub.trendhub.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
<<<<<<< HEAD

=======
import lombok.Data;
>>>>>>> c3fd21d (feat:코드수정(로그인 안됨))
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data// 왜 데이터 쓰고 null 값이 없어졌는가
// 우선은 @data 없어도 갑자기 되니까 내일 다시 테스트
public class FindUserDto {
    @NotBlank(message = "이름을 입력해주세요.")
    private String username;
    private String loginId;
    private String email;
}