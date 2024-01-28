package com.trendhub.trendhub.domain.user.service;

import com.trendhub.trendhub.domain.user.entity.User;
import com.trendhub.trendhub.domain.user.repository.UserRepository;
import com.trendhub.trendhub.global.config.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        validateDuplicateUser(user);
        return userRepository.save(user);
    }

    private void validateDuplicateUser(User user) {
        Optional<User> _findUser = userRepository.findByLoginId(user.getUsername());
        if (_findUser.isPresent()) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException{
        Optional<User> _user = userRepository.findByLoginId(loginId);

        if(_user == null) {
            throw new UsernameNotFoundException(loginId);
        }
        User user = _user.get();
        return SecurityUser.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .build();
    }

    public Optional<User> findByProviderAndProviderId(String provider, String providerId) {
        return userRepository.findByProviderAndProviderId(provider, providerId);
    }
}
