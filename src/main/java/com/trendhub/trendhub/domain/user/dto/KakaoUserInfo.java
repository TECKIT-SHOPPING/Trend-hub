package com.trendhub.trendhub.domain.user.dto;

import com.trendhub.trendhub.domain.user.entity.SocialProvider;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import static jakarta.persistence.EnumType.STRING;

@Data
@Builder
public class KakaoUserInfo {
    private Long id;
    private String nickname;
    private String profileImg;
    @Enumerated(STRING)
    private SocialProvider provider;

    public KakaoUserInfo(Long id, String nickname, String profileImg, SocialProvider provider) {
        this.id = id;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.provider = provider;
    }
}
