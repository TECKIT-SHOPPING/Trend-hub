package com.trendhub.trendhub.domain.email.dto;

import lombok.Data;

@Data
public class SignUpEmailReq {

    private String email;
    private String emailDomain;
}
