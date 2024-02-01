package com.trendhub.trendhub.domain.coordi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoordiDto {
    private long coordiId;
    private String image;
    private int totalLike;
    private Long likeId;
    private boolean liked;

    public CoordiDto(long coordiId, String image, int totalLike, Long likeId, boolean liked) {
        this.coordiId = coordiId;
        this.image = image;
        this.totalLike = totalLike;
        this.likeId = likeId;
        this.liked = liked;
    }

}
