package com.trendhub.trendhub.domain.review.repository;

import com.trendhub.trendhub.domain.review.dto.MypageReviewDto;
import com.trendhub.trendhub.domain.review.entity.Review;
import com.trendhub.trendhub.domain.user.entity.User;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepositoryCustom {

    List<MypageReviewDto> findByUser(User user);
}
