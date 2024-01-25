package com.trendhub.trendhub.domain.email.entity;

import com.trendhub.trendhub.domain.user.entity.User;
import com.trendhub.trendhub.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EmailAuth extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emailAuthId;

    private String email;
    private String authCode;
    private boolean emailChecked;


    public static EmailAuth createEmailAuth(String email, String authCode) {
        return EmailAuth.builder()
                .email(email)
                .authCode(authCode)
                .emailChecked(false)
                .build();
    }

    public void verifyEmail() {
        this.emailChecked = true;
    }

}