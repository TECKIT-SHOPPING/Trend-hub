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

    public Page<QnA> getQnAList(int page, Long productId) {
        Pageable pageable = PageRequest.of(page, 10);
        return this.qnaRepository.findByProduct_ProductId(pageable, productId);
    }

    public QnA getQnaDetail(Long productId) {
        Optional<QnA> qnA = this.qnaRepository.findById(productId);
        if(qnA.isPresent()) {
            return qnA.get();
        } else {
            throw new DataNotFoundException("qna not found");
        }
    }

    public void createQnaAnswer(QnaAnswerDto qnaAnswerDto, QnA qna, User user) {
        boolean roleFlag = user.getRole().equals("ADMIN");
        QnaAnswer saveQnaAnswer = qnaAnswerDto.toEntity(qna, user, roleFlag);
        this.qnaAnswerRepository.save(saveQnaAnswer);
    }

    public List<QnaAnswer> getQnaAnswer() {
        return qnaAnswerRepository.findAll();
    }

    public boolean canRead(User user, QnA qna) {
        if (qna == null) return false;

        if (!qna.isSecret()) return true;
        if (user == null) return false;
        if (user.getRole() != null && user.getRole().equals("ADMIN")) return true;

        return user.equals(qna.getUser());
    }
}
