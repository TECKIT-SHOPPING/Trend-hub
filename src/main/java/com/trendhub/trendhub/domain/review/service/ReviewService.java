package com.trendhub.trendhub.domain.review.service;


import com.trendhub.trendhub.domain.review.entity.Review;
import com.trendhub.trendhub.domain.review.repository.ReviewRepository;
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

    public List<Review> findByCoordi(Long id) {
        return reviewRepository.findByCoordiIdList(id);
    }

    public Page<Review> getReviewList(int page, Long coordiId) {
        Pageable pageable = PageRequest.of(page-1, 5);
        return reviewRepository.findByCoordiId(pageable, coordiId);
    }

}
