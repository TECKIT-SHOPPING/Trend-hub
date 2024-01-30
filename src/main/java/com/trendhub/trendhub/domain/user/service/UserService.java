package com.trendhub.trendhub.domain.user.service;

import com.trendhub.trendhub.domain.email.entity.EmailAuth;
import com.trendhub.trendhub.domain.email.repository.EmailAuthRepository;
import com.trendhub.trendhub.domain.user.dto.FindUserDto;
import com.trendhub.trendhub.domain.user.dto.SignupFormDto;
import com.trendhub.trendhub.domain.user.entity.SocialProvider;
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
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailAuthRepository emailAuthRepository;

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
                .username(user.getLoginId())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .build();
    }

    public Optional<User> findByProviderAndProviderId(SocialProvider provider, String providerId) {
        return userRepository.findByProviderAndProviderId(provider, providerId);

    }


    public User saveMember(SignupFormDto signupFormDto) {
        //필수 동의 체크
        validateSignupForm(signupFormDto);
        //이메일 인증했는지 체크
        validateEmailAuth(signupFormDto);

        //랜덤 닉네임 생성
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;
        while (userRepository.existsByNickname("user_" + randomNumber)) {
            randomNumber = random.nextInt(9000) + 1000;
        }
        String randomNickname = "user_" + randomNumber;

        //유저 생성
        User saveUser = signupFormDto.toEntity(signupFormDto, randomNickname, passwordEncoder);

        //중복된 회원인지 체크
        validateDuplicateUser(saveUser);

        //회원가입
        User user = userRepository.save(saveUser);
        return user;
    }

    private void validateSignupForm(SignupFormDto signupFormDto) {
        if (!signupFormDto.isAgreeInfo() || !signupFormDto.isAgreeAge()) { //필수 동의 체크
            throw new IllegalStateException("필수 동의를 해주세요.");
        }
        if (!signupFormDto.getPassword().equals(signupFormDto.getCheckPassword())) { // 비밀번호 일치 확인
            throw new IllegalStateException("비밀번호가 일치하지않습니다.");
        }
    }

    private void validateEmailAuth(SignupFormDto signupFormDto) {
        String email = signupFormDto.getEmail() + "@" + signupFormDto.getEmailDomain();
        System.out.println("email = " + email);
        EmailAuth emailAuth = emailAuthRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("인증하기 버튼을 눌러주세요."));
        if (!emailAuth.isEmailChecked()) {
            throw new IllegalStateException("인증확인 버튼을 눌러주세요.");
        }
    }

    public Optional<User> findUserByUsernameAndEmail(FindUserDto dto) {
        return userRepository.findByUsernameAndEmail(dto.getUsername(), dto.getEmail());
    }
}
