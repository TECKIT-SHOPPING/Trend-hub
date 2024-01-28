package com.trendhub.trendhub.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KakaoUserInfo {
    private Long id;
    private String nickname;
    private String profileImg;

    public KakaoUserInfo(Long id, String nickname, String profileImg) {
        this.id = id;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }
}
