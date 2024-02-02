package com.trendhub.trendhub.global.rq;

import com.trendhub.trendhub.domain.user.entity.User;
import com.trendhub.trendhub.global.config.security.SecurityUser;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Optional;

@Component
@RequestScope
@RequiredArgsConstructor
public class Rq {
    private final EntityManager entityManager;
    private User userInfo;

    public SecurityUser getUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .filter(it -> it instanceof SecurityUser)
                .map(it -> (SecurityUser) it)
                .orElse(null);
    }

    public boolean isLogin() {
        return getUser() != null;
    }

    public boolean isLogout() {
        return !isLogin();
    }

    public User getUserInfo() {
        if ( isLogout() ) return null;

        if ( userInfo == null ) {
            userInfo = entityManager.getReference(User.class, getUser().getId());
        }

        return userInfo;
    }
}