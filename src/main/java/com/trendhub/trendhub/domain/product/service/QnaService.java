package com.trendhub.trendhub.domain.product.service;

import com.trendhub.trendhub.domain.product.dto.QnaDto;
import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.product.entity.QnA;
import com.trendhub.trendhub.domain.product.repository.QnaRepository;
import com.trendhub.trendhub.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;

    public void createQna(QnaDto qnaDto, Product product, User user) {
        QnA saveQnA = qnaDto.toEntity(product, user);
        this.qnaRepository.save(saveQnA);
    }

    public List<QnA> getQnAList() {
        return qnaRepository.findAll();
    }

    public QnA getQnaDetail(Long productId) {
        Optional<QnA> qnA = this.qnaRepository.findById(productId);
        if(qnA.isPresent()) {
            return qnA.get();
        } else {
            throw new DataNotFoundException("qna not found");
        }
    }
}
