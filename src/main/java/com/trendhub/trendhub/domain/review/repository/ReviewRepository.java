package com.trendhub.trendhub.domain.review.repository;

import com.trendhub.trendhub.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAll(Pageable pageable);
    Page<Review> findByProduct_ProductId(Pageable pageable, Long productId);

    @Query("select r from Review r join fetch r.user where r.coordi.coordiId = :coordiId")
    List<Review> findByCoordiIdList(@Param("coordiId") Long coordiId);

    @Query("select r from Review r join fetch r.user where r.coordi.coordiId = :coordiId")
    Page<Review> findByCoordiId(Pageable pageable,@Param("coordiId")  Long coordiId);

}
