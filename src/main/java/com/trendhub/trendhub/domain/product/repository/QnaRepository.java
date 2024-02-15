package com.trendhub.trendhub.domain.product.repository;

import com.trendhub.trendhub.domain.product.entity.QnA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<QnA, Long> {
    Page<QnA> findByProduct_ProductId(Pageable pageable, Long productId);
    /*List<QnA> findByProductId(Long productId);*/
}
