package com.trendhub.trendhub.domain.coordi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoordiDto {
    private long coordiId;
    private String profile;
    private String nickname;
    private String image;
    private int totalLike;
    private boolean liked;

    public CoordiDto(long coordiId, String profile, String nickname, String image, int totalLike, boolean liked) {
        this.coordiId = coordiId;
        this.profile = profile;
        this.nickname = nickname;
        this.image = image;
        this.totalLike = totalLike;
        this.liked = liked;
    }
}
