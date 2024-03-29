package com.trendhub.trendhub.domain.user.repository;

import com.trendhub.trendhub.domain.user.entity.SocialProvider;
import com.trendhub.trendhub.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);
    Optional<User> findByProviderAndProviderId(SocialProvider provider, String providerId);
    Optional<User> findByUsernameAndEmail(String username, String email);
    boolean existsByNickname(String nickname);
    Optional<Object> findByNickname(String nickname);
    Optional<User> findByLoginIdAndEmail(String loginId, String email); // 비밀번호 찾기
}