package com.trendhub.trendhub.domain.email.repository;

import com.trendhub.trendhub.domain.email.entity.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long> {
    Optional<EmailAuth> findByEmailAndAuthCode(String email, String authCode);
    Optional<EmailAuth> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<EmailAuth> findByAuthCode(String authCode);

}
