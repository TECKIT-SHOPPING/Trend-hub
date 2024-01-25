package com.trendhub.trendhub.domain.user.entity;

import com.trendhub.trendhub.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String loginId;
    private String password;
    private String username;
    private String nickname;
    private String email;

    @Temporal(TemporalType.DATE)
    private Date birth;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));

        return authorities;
    }

    private String profile; // URL
    private String provider; //app, kakao
    private String providerId;  // 소셜 전용 ID 변수
    private LocalDateTime agreeInfo;
    private LocalDateTime agreeAge;
    private LocalDateTime agreeEmail;
    private String zipcode;
    private String address1; //주소
    private String address2; //상세주소
    private int level;
    private int point;
    private String status;

    private boolean emailAuthChecked;

    public void changeEmailAuthChecked(boolean emailAuthChecked) {
        this.emailAuthChecked = emailAuthChecked;
    }
}