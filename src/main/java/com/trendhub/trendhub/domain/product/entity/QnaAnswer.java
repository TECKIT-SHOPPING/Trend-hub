package com.trendhub.trendhub.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "qnaAnswer")
public class QnaAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "QnA_ID")
    private QnA qnA;    // 한 qna에 여러 관리자가 답글을 달 수 있음

    /*private ROLE role;*/  // 관리자 ADMIN 권한 부여, 추후에 추가할 예정

    private String loginId;
    private LocalDateTime createDate;
    private String name;
    private boolean answered;
}
