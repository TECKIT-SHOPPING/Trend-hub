package com.trendhub.trendhub.domain.review.service;


import com.trendhub.trendhub.domain.review.entity.ReviewDto;
import com.trendhub.trendhub.domain.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {
        private final ReviewRepository reviewRepository;

        public ReviewService(ReviewRepository reviewRepository) {
                this.reviewRepository = reviewRepository;
        }

        @Transactional(readOnly = true)
        public List<ReviewDto> getUserReviews(Long userId) {
                return reviewRepository.findByUserId(userId);
        }
}