package com.trendhub.trendhub.domain.user.service;

import com.trendhub.trendhub.domain.email.entity.EmailAuth;
import com.trendhub.trendhub.domain.email.repository.EmailAuthRepository;
import com.trendhub.trendhub.domain.user.dto.FindUserDto;
import com.trendhub.trendhub.domain.user.dto.SignupFormDto;
import com.trendhub.trendhub.domain.user.entity.SocialProvider;
import com.trendhub.trendhub.domain.user.entity.User;
import com.trendhub.trendhub.domain.user.repository.UserRepository;
import com.trendhub.trendhub.global.config.security.SecurityUser;
import com.trendhub.trendhub.global.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailAuthRepository emailAuthRepository;
    private final S3Service s3Service;
    private final EmailService emailService;

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
        return new SecurityUser(
                user.getUserId(),
                user.getLoginId(),
                user.getPassword(),
                user.getAuthorities());

//        return SecurityUser.builder()
//                .username(user.getLoginId())
//                .password(user.getPassword())
//                .authorities(user.getAuthorities())
//                .build();
    }

    public Optional<User> findByProviderAndProviderId(SocialProvider provider, String providerId) {
        return userRepository.findByProviderAndProviderId(provider, providerId);

    }


    public User saveMember(SignupFormDto signupFormDto) {
        //필수 동의 체크
        validateSignupForm(signupFormDto);
        //이메일 인증했는지 체크
        validateEmailAuth(signupFormDto);
        //중복된 아이디 체크
        validateLoginId(signupFormDto.getLoginId());


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


    private void validateLoginId(String loginId) {
        Optional<User> _findUser = userRepository.findByLoginId(loginId);
        if (_findUser.isPresent()) {
            throw new IllegalStateException("중복된 아이디입니다.");
        }
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

    @Transactional
    public void changePassword(User userInfo, ChangePasswordDto changePasswordDto) {
        if (!passwordEncoder.matches(changePasswordDto.getOriginPassword(), userInfo.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getCheckNewPassword())) {
            throw new IllegalArgumentException("새 비밀번호가 일치하지 않습니다.");
        }

        User changeUser = User.builder()
                .password(passwordEncoder.encode(changePasswordDto.getNewPassword()))
                .build();

        userInfo.changePassword(changeUser);
    }

    public void checkNickname(ChangeNicknameDto changeNicknameDto) {
        userRepository.findByNickname(changeNicknameDto.getNickname()).ifPresent(
                user -> {
                    throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
                }
        );
    }

    @Transactional
    public void changeNickname(User userInfo, ChangeNicknameDto changeNicknameDto) {
        checkNickname(changeNicknameDto);

        User changeUser = User.builder()
                .nickname(changeNicknameDto.getNickname())
                .build();

        userInfo.changeNickname(changeUser);
    }

    @Transactional
    public void changeProfile(User userInfo, MultipartFile profile) {
        String profileUrl = s3Service.createVideo(profile);

        User changeUser = User.builder()
                .profile(profileUrl)
                .build();

        userInfo.changeProfile(changeUser);
    }

    @Transactional
    public void saveAddress(User userInfo, AddressDto addressDto) {
        User changeUser = User.builder()
                .zipcode(addressDto.getZipcode())
                .address1(addressDto.getAddress1())
                .address2(addressDto.getAddress2())
                .build();

        userInfo.saveAddress(changeUser);
    }

    public void findId(String name, String email) throws Exception {
        Optional<User> _user = userRepository.findByUsernameAndEmail(name, email);
        if(_user.isEmpty()) throw new IllegalStateException("존재하지 않는 회원입니다.");

        String userId = _user.get().getLoginId();

        emailService.sendEmailId(email, userId);
    }

    public void findPw(String loginId, String email) throws Exception {
        Optional<User> _user = userRepository.findByLoginIdAndEmail(loginId, email);
        if(_user.isEmpty()) throw new IllegalStateException("존재하지 않는 회원입니다.");

        String tempPw = getTempPassword();

        User user = _user.get();
        user.setPassword(passwordEncoder.encode(tempPw));

        System.out.println("임시 비밀번호 입니다 = " + tempPw + "encode = " + user.getPassword());

        userRepository.save(user);

        emailService.sendEmailPw(email, tempPw);
    }

    public String getTempPassword() {
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return  str;
    }

    public User getUser(String longInid){
        Optional<User> user = this.userRepository.findByLoginId(longInid);
        return user.get();
    }

    public Optional<User> findUserByUsernameAndEmail(FindUserDto dto) {
        return userRepository.findByUsernameAndEmail(dto.getUsername(), dto.getEmail());
    }
}
