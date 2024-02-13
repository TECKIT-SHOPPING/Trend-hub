package com.trendhub.trendhub.domain.review.controller;



import com.trendhub.trendhub.domain.review.entity.ReviewDto;
import com.trendhub.trendhub.domain.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewDto>> getUserReviews(@PathVariable Long userId) {
        List<ReviewDto> reviews = reviewService.getUserReviews(userId);
        return ResponseEntity.ok(reviews);
    }
}