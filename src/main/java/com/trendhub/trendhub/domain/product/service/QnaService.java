package com.trendhub.trendhub.domain.product.service;

import com.trendhub.trendhub.domain.product.dto.QnaAnswerDto;
import com.trendhub.trendhub.domain.product.dto.QnaDto;
import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.product.entity.QnA;
import com.trendhub.trendhub.domain.product.entity.QnaAnswer;
import com.trendhub.trendhub.domain.product.repository.QnaAnswerRepository;
import com.trendhub.trendhub.domain.product.repository.QnaRepository;
import com.trendhub.trendhub.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;
    private final QnaAnswerRepository qnaAnswerRepository;

    public void createQna(QnaDto qnaDto, Product product, User user) {
        QnA saveQnA = qnaDto.toEntity(product, user);
        this.qnaRepository.save(saveQnA);
    }

    public Page<QnA> getQnAList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return this.qnaRepository.findAll(pageable);
    }

    public QnA getQnaDetail(Long productId) {
        Optional<QnA> qnA = this.qnaRepository.findById(productId);
        if(qnA.isPresent()) {
            return qnA.get();
        } else {
            throw new DataNotFoundException("qna not found");
        }
    }

    public void createQnaAnswer(QnaAnswerDto qnaAnswerDto, QnA qnA, User user) {
        QnaAnswer saveQnaAnswer = qnaAnswerDto.toEntity(qnA, user);
        this.qnaAnswerRepository.save(saveQnaAnswer);
    }

    public List<QnaAnswer> getQnaAnswer() {
        return qnaAnswerRepository.findAll();
    }
}
