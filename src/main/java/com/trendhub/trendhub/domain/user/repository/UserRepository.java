package com.trendhub.trendhub.domain.user.repository;

import com.trendhub.trendhub.domain.user.entity.SocialProvider;
import com.trendhub.trendhub.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);

<<<<<<< HEAD
    Optional<User> findByProviderAndProviderId(SocialProvider provider, String providerId);
=======
>>>>>>> origin/feature/register-user
    boolean existsByNickname(String nickname);
}