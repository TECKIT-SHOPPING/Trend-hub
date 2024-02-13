package com.trendhub.trendhub.domain.review.repository;

import com.trendhub.trendhub.domain.review.entity.Review;
import com.trendhub.trendhub.domain.review.entity.ReviewDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT new com.trendhub.trendhub.domain.review.entity.ReviewDto(r.reviewId, r.coordi.coordiId, r.product.productId, r.content, r.star) FROM Review r WHERE r.user.userId = :userId")
    List<ReviewDto> findByUserId(@Param("userId") Long userId);
}