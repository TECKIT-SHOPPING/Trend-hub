package com.trendhub.trendhub.domain.email.entity;

import com.trendhub.trendhub.global.entity.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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