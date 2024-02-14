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

import static jakarta.persistence.EnumType.STRING;


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

        if (this.role != null && this.role.equals("ADMIN")) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }
        else {
            authorities.add(new SimpleGrantedAuthority("MEMBER"));
        }
        return authorities;
    }

    private String profile; // URL

    @Enumerated(STRING)
    private SocialProvider provider;    // 카카오 기준으로 값 넣기, 없다면 null
    private String providerId;  // 소셜 전용 ID 변수

    private String role;

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

    public void changePassword(User changeUser) {
        this.password = changeUser.getPassword();
    }

    public void changeNickname(User changeUser) {
        this.nickname = changeUser.getNickname();
    }

    public void changeProfile(User changeUser) {
        this.profile = changeUser.getProfile();
    }

    public void saveAddress(User changeUser) {
        this.zipcode = changeUser.getZipcode();
        this.address1 = changeUser.getAddress1();
        this.address2 = changeUser.getAddress2();
    }
}