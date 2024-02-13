package com.trendhub.trendhub.domain.review.controller;

import com.trendhub.trendhub.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ApiReviewController {

    private final ReviewService reviewService;

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable("reviewId") Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("정상적으로 삭제되었습니다.");
    }
}
