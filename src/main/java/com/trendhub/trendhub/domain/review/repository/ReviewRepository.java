package com.trendhub.trendhub.domain.review.repository;

import com.trendhub.trendhub.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

//    @Query("SELECT r FROM Review r " +
//            "JOIN FETCH r.product p " +
//            "JOIN FETCH OrderDetail od ON od.product = p " +
//            "JOIN FETCH od.order o " +
//            "WHERE r.user = :user")
//    List<Review> findByUser(@Param("user") User user);
    Page<Review> findAll(Pageable pageable);
}
