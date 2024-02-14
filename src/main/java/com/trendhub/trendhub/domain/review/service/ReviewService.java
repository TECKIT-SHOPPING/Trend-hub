package com.trendhub.trendhub.domain.review.service;

import com.trendhub.trendhub.domain.review.dto.MypageReviewDto;
import com.trendhub.trendhub.domain.review.entity.Review;
import com.trendhub.trendhub.domain.review.repository.ReviewRepository;
import com.trendhub.trendhub.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<MypageReviewDto> findByUser(User user) {
        List<MypageReviewDto> mypageReviewDtos = reviewRepository.findByUser(user);
        return mypageReviewDtos;
    }

    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalStateException("존재하는 리뷰가 없습니다."));
        reviewRepository.delete(review);
    }

    public Page<Review> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return this.reviewRepository.findAll(pageable);
    }
    public List<Review> findByCoordi(Long id) {
        return reviewRepository.findByCoordiIdList(id);
    }

    public Page<Review> getReviewList(int page, Long coordiId) {
        Pageable pageable = PageRequest.of(page-1, 5);
        return reviewRepository.findByCoordiId(pageable, coordiId);
    }

}
