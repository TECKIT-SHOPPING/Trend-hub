package com.trendhub.trendhub.domain.coordi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoordiDetailDto {
    private long coordiId;
    private String image;
    private int totalLike;
    private boolean liked;

    public CoordiDetailDto(long coordiId, String image, int totalLike, boolean liked) {
        this.coordiId = coordiId;
        this.image = image;
        this.totalLike = totalLike;
        this.liked = liked;
    }
}
