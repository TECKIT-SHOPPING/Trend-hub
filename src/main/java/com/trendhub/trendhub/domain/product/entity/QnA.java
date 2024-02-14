package com.trendhub.trendhub.domain.product.entity;

import com.trendhub.trendhub.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "qna")
public class QnA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String inquireType;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "qnA", cascade = CascadeType.REMOVE)
    private List<QnaAnswer> qnaAnswerList;

    private String loginId;

    private LocalDateTime createDate;

    private String name;
    private String image;
    private int price;
    private boolean answered;
    private boolean secret;
}
