package com.trendhub.trendhub.domain.user.entity;

import com.trendhub.trendhub.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String loginId;
    private String password;
    private String username;
    private String nickname;

    @Temporal(TemporalType.DATE)
    private Date birth;

    private String profile;
    private String provider; //app, kakao
    private LocalDateTime agreeInfo;
    private LocalDateTime agreeAge;
    private LocalDateTime agreeEmail;
    private String zipcode;
    private String address1; //주소
    private String address2; //상세주소
    private int level;
    private int point;
    private String status;
}
